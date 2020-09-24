package io.lizardframework.data.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xueqi
 * @date 2020-09-24
 */
@Repository
public interface OrmMixedDAO extends JpaRepository<OrmMixedDAO, Long> {
}
