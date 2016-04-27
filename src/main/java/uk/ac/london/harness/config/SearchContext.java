package uk.ac.london.harness.config;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages = { "uk.ac.london.harness.student" }, multicoreSupport = true)
public class SearchContext {

	@Bean
	public SolrServer solrServer(@Value("${solr.server.url}") String solrHost) {
		return new HttpSolrServer(solrHost);
	}

}