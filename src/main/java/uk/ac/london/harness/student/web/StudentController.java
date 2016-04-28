package uk.ac.london.harness.student.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.ac.london.harness.student.StudentService;

@Controller
@Component
@Scope("prototype")
public class StudentController {

	private StudentService studentService;

	@RequestMapping("/students/{id}")
	public String search(Model model, @PathVariable("id") String id, HttpServletRequest request) {
		model.addAttribute("student", studentService.findById(id));
		return "student";
	}

	@Autowired
	public void setProductService(StudentService studentService) {
		this.studentService = studentService;
	}

}