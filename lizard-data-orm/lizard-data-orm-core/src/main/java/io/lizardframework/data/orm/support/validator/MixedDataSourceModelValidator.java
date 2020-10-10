package io.lizardframework.data.orm.support.validator;

import io.lizardframework.data.enums.MasterSlaveType;
import io.lizardframework.data.orm.model.AtomDataSourceModel;
import io.lizardframework.data.orm.model.MixedDataSourceModel;
import io.lizardframework.data.orm.model.RepositoryDataSourceModel;
import io.lizardframework.data.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xueqi
 * @date 2020-09-29
 */
public class MixedDataSourceModelValidator implements Validator<MixedDataSourceModel> {

	@Override
	public void validate(MixedDataSourceModel model) {
		String mixedDataSourceName = model.getMixedName();

		// mixed datasource checker
		if (model.getState() == null)
			throw new IllegalArgumentException(mixedDataSourceName + " state is null.");
		if (model.getType() == null)
			throw new IllegalArgumentException(mixedDataSourceName + " db type is null.");
		if (CollectionUtils.isEmpty(model.getRepositories()))
			throw new IllegalArgumentException(mixedDataSourceName + " repositories is empty.");

		// repository checker
		List<RepositoryDataSourceModel> repositories      = model.getRepositories();
		Set<String>                     repositoryNameSet = new HashSet<>(repositories.size());
		repositories.forEach(repository -> {
			String repositoryName = repository.getRepositoryName();

			if (repositoryNameSet.contains(repositoryName))
				throw new IllegalArgumentException(mixedDataSourceName + " repository: " + repositoryName + " already exist.");
			repositoryNameSet.add(repositoryName);

			if (repository.getState() == null)
				throw new IllegalArgumentException(mixedDataSourceName + " repository:" + repositoryName + " state is null.");
			if (repository.getLoadBalance() == null)
				throw new IllegalArgumentException(mixedDataSourceName + " repository:" + repositoryName + " loadbalance is null.");

			// atom checker
			if (CollectionUtils.isEmpty(repository.getAtoms()))
				throw new IllegalArgumentException(mixedDataSourceName + " repository:" + repositoryName + " atoms is empty.");

			List<AtomDataSourceModel> atoms       = repository.getAtoms();
			Set<String>               atomNameSet = new HashSet<>(atoms.size());
			int                       masterNums  = 0;
			for (AtomDataSourceModel atom : atoms) {
				String atomName = atom.getAtomName();

				if (atomNameSet.contains(atomName))
					throw new IllegalArgumentException(mixedDataSourceName + " repository:" + repositoryName + ", atom:" + atomName + " already exist.");
				atomNameSet.add(atomName);

				if (atom.getState() == null)
					throw new IllegalArgumentException(mixedDataSourceName + " repository:" + repositoryName + ", atom:" + atomName + " state is null.");
				if (atom.getMasterSlaveType() == null)
					throw new IllegalArgumentException(mixedDataSourceName + " repository:" + repositoryName + ", atom:" + atomName + " master slave type is null.");
				if (atom.getDataSourcePoolType() == null)
					throw new IllegalArgumentException(mixedDataSourceName + " repository:" + repositoryName + ", atom:" + atomName + " datasource pool type is null.");
				if (StringUtils.isEmpty(atom.getHost()) || atom.getPort() == 0 || StringUtils.isEmpty(atom.getUsername())
						|| StringUtils.isEmpty(atom.getPassword()) || StringUtils.isEmpty(atom.getPassword()))
					throw new IllegalArgumentException(mixedDataSourceName + " repository:" + repositoryName + ", atom:" + atomName + " jdbc connection info error.");

				if (MasterSlaveType.MASTER.equals(atom.getMasterSlaveType()))
					masterNums = masterNums + 1;
			}

			if (masterNums != 1)
				throw new IllegalArgumentException(mixedDataSourceName + " repository:" + repositoryName + " master atom must be only one. actual:" + masterNums);
		});
	}
}
