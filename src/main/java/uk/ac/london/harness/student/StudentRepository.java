package uk.ac.london.harness.student;

import java.util.Collection;

import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.solr.core.query.Query.Operator;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import uk.ac.london.harness.student.model.Student;

@RepositoryRestResource
public interface StudentRepository extends SolrCrudRepository<Student, String> {

	@Highlight(prefix = "<b>", postfix = "</b>")
	@Query(fields = { SearchableStudentDefinition.ID_FIELD_NAME, SearchableStudentDefinition.NAME_FIELD_NAME,
			SearchableStudentDefinition.FILESIZE_FIELD_NAME,
			SearchableStudentDefinition.CAMEL_FIELD_NAME }, defaultOperator = Operator.AND)
	HighlightPage<Student> findByNameIn(Collection<String> names, Pageable page);

	@Facet(fields = { SearchableStudentDefinition.NAME_FIELD_NAME })
	FacetPage<Student> findByNameStartsWith(Collection<String> nameFragments, Pageable pagebale);

}