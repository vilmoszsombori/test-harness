package uk.ac.london.harness.student.web;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.london.harness.student.StudentService;
import uk.ac.london.harness.student.model.Student;

@Controller
@Component
@Scope("prototype")
public class SearchController {

	private StudentService studentService;

	@RequestMapping("/search")
	public String search(Model model, @RequestParam(value = "q", required = false) String query,
			@PageableDefault(page = 0, size = StudentService.DEFAULT_PAGE_SIZE) Pageable pageable,
			HttpServletRequest request) {

		model.addAttribute("page", studentService.findByName(query, pageable));
		model.addAttribute("pageable", pageable);
		model.addAttribute("query", query);
		return "search";
	}

	@ResponseBody
	@RequestMapping(value = "/autocomplete", produces = "application/json")
	public Set<String> autoComplete(Model model, @RequestParam("term") String query,
			@PageableDefault(page = 0, size = 1) Pageable pageable) {
		if (!StringUtils.hasText(query)) {
			return Collections.emptySet();
		}

		FacetPage<Student> result = studentService.autocompleteNameFragment(query, pageable);

		Set<String> titles = new LinkedHashSet<String>();
		for (Page<FacetFieldEntry> page : result.getFacetResultPages()) {
			for (FacetFieldEntry entry : page) {
				if (entry.getValue().contains(query)) { 
					// we have to do this as we do not use terms vector or a string field
					titles.add(entry.getValue());
				}
			}
		}
		return titles;
	}

	@Autowired
	public void setProductService(StudentService studentService) {
		this.studentService = studentService;
	}

}