package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//Controller返回的一些html页面
@Controller
public class HelloController {

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String index() {
        return "index";
    }
    
    @RequestMapping("/greeting2")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    
    //以下两个是表单提交的处理 http://spring.io/guides/gs/handling-form-submission/
    @RequestMapping(value="/form", method=RequestMethod.GET)
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "form-submit";
    }

    @RequestMapping(value="/form", method=RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
        model.addAttribute("greeting", greeting);
        return "form-result";
    }
}
