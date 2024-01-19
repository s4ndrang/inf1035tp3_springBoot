package TP3San.drugProject.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class LogAdvice {

    @Autowired
    private ILogRepo logRepo;

    public LogAdvice(ILogRepo logRepo) {
        this.logRepo = logRepo;
    }

    @Before("execution(* TP3San.drugProject.drug.DrugController.*(..))")
    public void saveLog(JoinPoint jp) {
        String username = "IdFictif";

        String action = extractActionFromMethod(jp);

        logRepo.save(new Log(username, action, LocalDateTime.now()));
    }

    private String extractActionFromMethod(JoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        if (method.getName().equals("getAllDrugs")) {
            return "list";
        } else if (method.getName().equals("registerNewDrug")) {
            return "add";
        } else if (method.getName().equals("deleteDrug")) {
            return "delete";
        } else if (method.getName().equals("updateDrug")) {
            return "update";
        } else if (method.getName().equals("getUnDrug")) {
            return "search";
        } return null; // If username is not found
    }
}
