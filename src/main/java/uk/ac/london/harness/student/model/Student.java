package uk.ac.london.harness.student.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import uk.ac.london.harness.student.SearchableStudentDefinition;

@SolrDocument(solrCoreName = "co3326")
public class Student implements SearchableStudentDefinition {

	private @Id @Indexed String id;

	private @Indexed(NAME_FIELD_NAME) String name;

	private @Indexed(FILESIZE_FIELD_NAME) boolean filesize;

	private @Indexed(CAMEL_FIELD_NAME) String camel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFilesize() {
		return filesize;
	}

	public void setFilesize(boolean filesize) {
		this.filesize = filesize;
	}

	public String getCamel() {
		return camel;
	}

	public void setCamel(String camel) {
		this.camel = camel;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", filesize=" + filesize + ", camel=" + camel + "]";
	}

}