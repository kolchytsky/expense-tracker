package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.domain.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.ROOT_CATEGORY_NAME;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 4:54 PM
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

    /**
     * As each domain is supposed to always have one and only one root category,
     * this method is used to retrieve it.
     * @return A root category for a domain corresponding to the supplied identifier.
     * */
    @Query("select c from Category c where c.domain.id = :domainId and c.name = '" + ROOT_CATEGORY_NAME + "'")
    Category getDomainRootCategory(@Param("domainId") Long domainId);

}
