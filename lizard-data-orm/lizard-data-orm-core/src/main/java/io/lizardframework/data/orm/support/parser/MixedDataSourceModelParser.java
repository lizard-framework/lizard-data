package io.lizardframework.data.orm.support.parser;

import io.lizardframework.data.enums.MixedType;
import io.lizardframework.data.orm.model.AtomDataSourceModel;
import io.lizardframework.data.orm.model.MixedDataSourceModel;
import io.lizardframework.data.orm.spring.register.meta.MixedDataSourceRegisterMBean;
import io.lizardframework.data.orm.support.validator.MixedDataSourceModelValidator;
import io.lizardframework.data.remoting.impl.MixedConfigFetcher;
import io.lizardframework.data.remoting.impl.SecurityFetcher;
import io.lizardframework.data.utils.JSONUtils;
import io.lizardframework.data.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * convert mixed datasource json to MixedDataSourceModel
 *
 * @author xueqi
 * @date 2020-10-11
 */
public class MixedDataSourceModelParser {
	private static final SecurityFetcher                 SECURITY_FETCHER                  = SecurityFetcher.getInstance();
	private static final Validator<MixedDataSourceModel> MIXED_DATA_SOURCE_MODEL_VALIDATOR = new MixedDataSourceModelValidator();


	public static MixedDataSourceModel parse(MixedDataSourceRegisterMBean mixedDataSourceRegisterMBean) throws Exception {
		String mixedDataSourceName = mixedDataSourceRegisterMBean.getMixedDataSourceName();

		// get json config
		String               modelJson            = MixedConfigFetcher.getInstance().getMixedConfig(mixedDataSourceName, MixedType.ORM);
		MixedDataSourceModel mixedDataSourceModel = JSONUtils.getDefaultGson().fromJson(modelJson, MixedDataSourceModel.class);

		// encode database password and username
		if (!CollectionUtils.isEmpty(mixedDataSourceModel.getRepositories())) {
			mixedDataSourceModel.getRepositories().forEach(repository -> {
				List<AtomDataSourceModel> atoms = repository.getAtoms();
				if (!CollectionUtils.isEmpty(atoms)) {
					atoms.forEach(atom -> {
						try {
							atom.setUsername(SECURITY_FETCHER.decrypt(atom.getUsername()));
							atom.setPassword(SECURITY_FETCHER.decrypt(atom.getPassword()));
						} catch (Exception e) {
							throw new ContextedRuntimeException("Decrypt error. mixed datasource:" + mixedDataSourceName
									+ " ,repository:" + repository.getRepositoryName()
									+ " , atom:" + atom.getAtomName(), e);
						}
					});
				}
			});
		}

		MIXED_DATA_SOURCE_MODEL_VALIDATOR.validate(mixedDataSourceModel);
		return mixedDataSourceModel;
	}

}
