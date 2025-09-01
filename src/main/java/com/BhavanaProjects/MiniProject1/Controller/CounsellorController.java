package com.BhavanaProjects.MiniProject1.Controller;
import com.BhavanaProjects.MiniProject1.DTO.DashboardResponseDto;
import com.BhavanaProjects.MiniProject1.Model.Counsellor;
import com.BhavanaProjects.MiniProject1.Service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class CounsellorController {

    @Autowired
    private CounsellorService  counsellorService;

    @GetMapping("/")
    public String index(Model model) {
        Counsellor counsellor = new Counsellor();
        model.addAttribute("counsellor", counsellor);
        return "index";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("counsellor") Counsellor counsellor, Model model, HttpServletRequest request) {
        Counsellor c = counsellorService.loginCnslr(counsellor.getCnslrEmail(),counsellor.getCnslrPwd());
    if(c==null){
        model.addAttribute("emsg","Invalid credentials");
        return "index";
    }else{
        HttpSession session = request.getSession(true);
        session.setAttribute("CID",c.getCnslrId());
        return "redirect:/dashboard";
    }
    }
    @GetMapping("/register")//To fill the registeration form
    public String register(Model model) {
        Counsellor cobj = new Counsellor();
        model.addAttribute("counsellor", cobj);
        return "register";
    }

    @PostMapping("/register")//To submit registeration form
    public String handleRegister(Counsellor  counsellor, Model model, HttpServletRequest request) {
        boolean register=counsellorService.registerCnslr(counsellor);
        if(register){
           model.addAttribute("smsg","Registeration success");
        }else {
            model.addAttribute("emsg","Registeration failed");
        }
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        Integer counsellorId=(Integer)session.getAttribute("CID");
        DashboardResponseDto dashboardInfoObj=counsellorService.getDashboardInfo(counsellorId);
        model.addAttribute("dashboardInfo",dashboardInfoObj);
        return "dashboard";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }
}
