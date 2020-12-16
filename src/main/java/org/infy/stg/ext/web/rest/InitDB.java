package org.infy.stg.ext.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.infy.stg.domain.*;
import org.infy.stg.ext.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController("extInitDB")
@RequestMapping("/api/ext")
public class InitDB {

    private final Logger log = LoggerFactory.getLogger(InterviewQuestionResource.class);


    public static final boolean FAKER=true;

    @Autowired
    private InterviewQuestionService interviewQuestionService;
    @Autowired
    private QuestionTagService questionTagService;
    @Autowired
    private IntervieweeService intervieweeService;
    @Autowired
    private QuestionSetService questionSetService;
    @Autowired
    private QTagService qTagService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private InterviewQuestionSetService interviewQuestionSetService;
    @Autowired
    private QuestionSetTagService questionSetTagService;

    @GetMapping("/db/init")
    public ResponseEntity<Object> initDb() {
        log.debug("REST request to get initialize dbl̥");
        initializeFakeData();
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert("applicationName", false, "ENTITY_NAME", "l̥s")).build();

    }

    @GetMapping("/db/truncate")
    public ResponseEntity<Object> truncateDb() {
        log.debug("REST request to get initialize dbl̥");
        truncateAll();
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert("applicationName", false, "ENTITY_NAME", "l̥s")).build();

    }


    public void initializeFakeData() {

       if(!FAKER) return;

        List<QTag> qTags =Stream.of("python","digital design engineer").map(s -> {
                            QTag qTag = new QTag();
                            qTag.setName(s);
                            qTag=qTagService.save(qTag);
                            return qTag;
                        }).collect(Collectors.toList());

        qTags.stream().map(qTag -> {
            Stream stream = null;
            switch (qTag.getName().toLowerCase()){
                case ("python"):{
                    stream = Stream.of(
                            Arrays.asList("How is Python an interpreted language?", "An interpreted language is any programming language which is not in machine level code before runtime. Therefore, Python is an interpreted language.", 1),
                            Arrays.asList("What is pep 8?", "PEP stands for Python Enhancement Proposal. It is a set of rules that specify how to format Python code for maximum readability.", 1),
                            Arrays.asList("How is memory managed in Python?", "Memory management in python is managed by Python private heap space. All Python objects and data structures are located in a private heap. The programmer does not have access to this private heap. The python interpreter takes care of this instead.", 1),
                            Arrays.asList("What is namespace in Python?", "A namespace is a naming system used to make sure that names are unique to avoid naming conflicts.", 2),
                            Arrays.asList("What is PYTHONPATH?", "It is an environment variable which is used when a module is imported. Whenever a module is imported, PYTHONPATH is also looked up to check for the presence of the imported modules in various directories. The interpreter uses it to determine which module to load.", 2),
                            Arrays.asList("What are python modules? Name some commonly used built-in modules in Python?", "Python modules are files containing Python code. This code can either be functions classes or variables. A Python module is a .py file containing executable code. Some of the commonly used built-in modules are: os sys math random data time JSON", 2),
                            Arrays.asList("What is self in Python?", "Self is an instance or an object of a class. In Python, this is explicitly included as the first parameter. However, this is not the case in Java where it’s optional.  It helps to differentiate between the methods and attributes of a class with local variables.", 3)
                    );
                    break;
                }
                case ("digital design engineer"):{
                    stream = Stream.of(
                            Arrays.asList("What is CMOS?", "The term CMOS stands for Complementary Metal Oxide Semiconductor. CMOS technology is one of the most popular technology in the computer chip design industry and broadly used today to form integrated circuits in numerous and varied applications.In CMOS technology, both N-type and P-type transistors are used to design logic functions. The same signal which turns ON a transistor of one type is used to turn OFF a transistor of the other type. This characteristic allows the design of logic devices using only simple switches, without the need for a pull-up resistor.", 1),
                            Arrays.asList("What is difference between Verilog full case and parallel case?", "A FULL case statement is a case statement in which all possible case-expression binary patterns can be matched to a case item or to a case default. If a case statement does not include a case default and if it is possible to find a binary case expression that does not match any of the defined case items, the case statement is not FULL. A PARALLEL case statement is a case statement in which it is only possible to match a case expression to one and only one case item. If it is possible to find a case expression that would match more than one case item, the matching case items are called OVERLAPPING case items and the case statement is not Parallel", 1),
                            Arrays.asList("What is pli in Varilog? Why is it used?", "What is pli in Varilog? Why is it used?\",\"a\":\"Programming Language Interface (PLI) of Verilog HDL is a mechanism to interface Verilog programs with programs written in C language. It also provides mechanism to access internal databases of the simulator from the C program.PLI is used for implementing system calls which would have been hard to do otherwise (or impossible) using Verilog syntax. Or, in other words, you can take advantage of both the paradigms - parallel and hardware related features of Verilog and sequential flow of C - using PLI.", 2),
                            Arrays.asList("What is gate delay?", "Propagation delay, or gate delay, is the length of time which starts when the input to a logic gate becomes stable and valid to change, to the time that the output of that logic gate is stable and valid to change.", 2),
                            Arrays.asList("What Are Circuit Board Parasitics?", "Circuit board parasitics are unwanted intrinsic electrical elements that are created on circuit boards by virtue of their proximity to actual circuit elements. These intrinsic elements may exist along or between any conductive elements (e.g. along traces or between adjacent component pins). These parasitics are part of the reason for the less than ideal electrical behavior of circuit elements.", 2),
                            Arrays.asList("What is an FPGA?", "Field Programmable Gate Arrays (FPGAs) are semiconductor devices that are based around a matrix of configurable logic blocks (CLBs) connected via programmable interconnects. FPGAs can be reprogrammed to desired application or functionality requirements after manufacturing. This feature distinguishes FPGAs from Application Specific Integrated Circuits (ASICs), which are custom manufactured for specific design tasks.", 3)
                    );
                    break;
                }

            }

            return Arrays.asList(qTag, stream);

        }).map(objects -> {
            return ((Stream<List>)objects.get(1)).map(s -> {
                Question question = new Question();
                question.setQuestion(s.get(0).toString());
                question.setAnswer(s.get(1).toString());
                question.setWeight((Integer) s.get(2));
                question = questionService.save(question);

                return Arrays.asList(objects.get(0),question);
            }).collect(Collectors.toList());
//
        }).collect(Collectors.toList()).forEach(lists -> {
            lists.forEach(objects -> {
                QTag qTag = (QTag) objects.get(0);
                Question question = (Question) objects.get(1);

                QuestionTag questionTag = new QuestionTag();
                questionTag.setQtag(qTag);
                questionTag.setQuestion(question);
                questionTagService.save(questionTag);

            });
        });



        Stream.of(
                Arrays.asList("python and digital",  "this is python and digital", "given python and digital"),
                Arrays.asList("python beginner",  "this is python", "taken python"),
                Arrays.asList("digital design engineer basic",  "this is digital design","given digital design")

        ).forEach(qs -> {

            QuestionSet questionSet = new QuestionSet();
            questionSet.setName(qs.get(0).toString());
            questionSet.setIntro(qs.get(1).toString());
            questionSet.setOutro(qs.get(2).toString());
            questionSetService.save(questionSet);
        });

        Stream.of(
                Arrays.asList("python and digital",4,"python"),
                Arrays.asList("python and digital",3,"digital design engineer"),
                Arrays.asList("python beginner", 5,"python" ),
                Arrays.asList("digital design engineer basic", 3,"digital design engineer")
        ).forEach(qts -> {

            QuestionSetTag questionSetTag = new QuestionSetTag();
            questionSetTag.setQuestionset(questionSetService.getQuestionSetByName(qts.get(0).toString()));
            questionSetTag.setqCount((Integer) qts.get(1));
            questionSetTag.setQtag(qTagService.findByQTagName(qts.get(2).toString()));
            questionSetTagService.save(questionSetTag);
        });

    }

    public void truncateAll(){
        questionSetTagService.findAll().forEach(obj -> questionSetTagService.delete(obj.getId()));
        interviewQuestionService.findAll().forEach(obj -> interviewQuestionService.delete(obj.getId()));
        interviewQuestionSetService.findAll().forEach(obj -> interviewQuestionSetService.delete(obj.getId()));
        intervieweeService.findAll().forEach(obj -> intervieweeService.delete(obj.getId()));
        questionTagService.findAll().forEach(obj -> questionTagService.delete(obj.getId()));
        questionSetService.findAll().forEach(obj -> questionSetService.delete(obj.getId()));
        qTagService.findAll().forEach(obj -> qTagService.delete(obj.getId()));
        questionService.findAll().forEach(obj -> questionService.delete(obj.getId()));

    }

}
