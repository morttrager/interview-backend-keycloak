package org.infy.stg.ext.web.rest;

import org.infy.stg.domain.*;
import org.infy.stg.ext.service.*;
import org.infy.stg.ext.service.dto.IntervieweeDataDTO;
import org.infy.stg.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


/**
 * REST controller for managing {@link org.infy.stg.domain.InterviewQuestion}.
 */
@RestController("extInterviewQuestionResource")
@RequestMapping("/api/ext")
public class InterviewQuestionResource extends org.infy.stg.web.rest.InterviewQuestionResource {

    private final Logger log = LoggerFactory.getLogger(InterviewQuestionResource.class);

    private static final String ENTITY_NAME = "interviewDbInterviewQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterviewQuestionService interviewQuestionService;
    @Autowired
    private QuestionTagService questionTagService;
    @Autowired
    private IntervieweeService intervieweeService;
    @Autowired
    private QuestionSetService questionSetService;
    @Autowired
    private InterviewQuestionSetService interviewQuestionSetService;
    @Autowired
    private QuestionSetTagService questionSetTagService;

    public InterviewQuestionResource(InterviewQuestionService interviewQuestionService) {
        super(interviewQuestionService);
        this.interviewQuestionService = interviewQuestionService;

    }

    @GetMapping("/interview-questions/current/{candidateId}")
    public ResponseEntity<InterviewQuestion> getCurrentQuestion(@PathVariable String candidateId) {
        log.debug("REST request to get InterviewQuestion : {}", candidateId);

        Optional<Interviewee> interviewee2 = intervieweeService.getIntervieweeByCandidateId(candidateId);

        if (interviewee2.isPresent()) {

            Optional<InterviewQuestion> interviewQuestion = interviewQuestionService.currentQuestion(interviewee2.get().getId());
            return ResponseUtil.wrapOrNotFound(interviewQuestion);
        } else {
            return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, candidateId)).build();
        }
    }

    @GetMapping("/interview-questions/interviewee/questions/{interviewQuestionSetId}")
    public List<IntervieweeDataDTO> getIntervieweeQuestions(@PathVariable Long interviewQuestionSetId) {
        List<IntervieweeDataDTO> intervieweeDataDTOS = new ArrayList<IntervieweeDataDTO>();


        List<InterviewQuestion> interviewQuestions = interviewQuestionService.questionsAsked(interviewQuestionSetId);

        interviewQuestions.stream().forEach(interviewQuestion -> {
            IntervieweeDataDTO intervieweeDataDTO = new IntervieweeDataDTO();
            intervieweeDataDTO.seInterviewQuestion(interviewQuestion);
            intervieweeDataDTO.seQuestionTags(questionTagService.getQTags(interviewQuestion.getQuestion().getId()));
            intervieweeDataDTOS.add(intervieweeDataDTO);
        });

        return  intervieweeDataDTOS;

    }



    @PostMapping("/interview-questions/update/score/{interviewQuestionId}")
    public ResponseEntity<InterviewQuestion> updateScore(@PathVariable Long interviewQuestionId, @NotNull @RequestBody Map<String,Object> json) throws URISyntaxException {
        log.debug("REST request to update InterviewQuestionId {} with score {}", interviewQuestionId, json);

        if (!interviewQuestionService.findOne(interviewQuestionId).isPresent()) {
            throw new BadRequestAlertException("An InterviewQuestion with ID not found", ENTITY_NAME, "idnotexists");
        }
//        interviewQuestionService.updateScore(interviewQuestionId, score);
        float score = ((Number) json.get("score")).floatValue();
        String response = json.get("response").toString();
        InterviewQuestion interviewQuestion = interviewQuestionService.findOne(interviewQuestionId).get();
        interviewQuestion.setScore(score);
        interviewQuestion.setRespone(response);
        interviewQuestion.setTime(Instant.now());
        interviewQuestionService.save(interviewQuestion);

        List<InterviewQuestionSet> interviewQuestionSets =interviewQuestionSetService.findByIntervieweeIdAndActive(interviewQuestion.getInterviewee().getId(), Boolean.TRUE);
        InterviewQuestionSet interviewQuestionSet = interviewQuestionSets.get(0);

        if(interviewQuestionSet.getNetScore()==null)
            interviewQuestionSet.setNetScore(0f);
        interviewQuestionSet.setNetScore(interviewQuestionSet.getNetScore()+score*interviewQuestion.getQuestion().getWeight());

        if(interviewQuestionSet.getNetQuestions()==null)
            interviewQuestionSet.setNetQuestions(0);
        interviewQuestionSet.setNetQuestions(interviewQuestionSet.getNetQuestions()+1);

        if(interviewQuestionSet.getNetWeight()==null)
            interviewQuestionSet.setNetWeight(0);
        interviewQuestionSet.setNetWeight(interviewQuestionSet.getNetWeight()+interviewQuestion.getQuestion().getWeight());

        interviewQuestionSetService.save(interviewQuestionSet);
        advanceState(interviewQuestion.getInterviewee().getId());
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, interviewQuestionId.toString())).build();
    }

    @PostMapping("/interview-questions/abandon/{interviewQuestionId}")
    public ResponseEntity<InterviewQuestion> terminateInterview(@PathVariable Long interviewQuestionId) throws URISyntaxException {
        log.debug("REST request to terminate the interview by setting score to 0 of interviewQuestion {}", interviewQuestionId);
        if (!interviewQuestionService.findOne(interviewQuestionId).isPresent()) {
            throw new BadRequestAlertException("An InterviewQuestion with ID not found", ENTITY_NAME, "idnotexists");
        }
        InterviewQuestion interviewQuestion = interviewQuestionService.findOne(interviewQuestionId).get();
        interviewQuestion.setScore(0f);
        interviewQuestion.setTime(Instant.now());
        interviewQuestionService.save(interviewQuestion);

        InterviewQuestionSet interviewQuestionSet =  interviewQuestionSetService.findByIntervieweeIdAndActive(interviewQuestion.getInterviewee().getId(),Boolean.TRUE).get(0);
        interviewQuestionSet.setAbandoned(Boolean.TRUE);
        interviewQuestionSet.setActive(Boolean.FALSE);
        interviewQuestionSetService.save(interviewQuestionSet);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, interviewQuestionId.toString())).build();
    }


    @PostMapping("/interview-questions/initialize/{candidateId}")
    public ResponseEntity<InterviewQuestion> initializeInterview(@PathVariable @NotNull String candidateId, @RequestBody @NotNull String questionSet) throws URISyntaxException {
        log.debug("REST request to initialize interview for Interviewee with candidate_id {} on topic {}", candidateId, questionSet);

        Optional<Interviewee> optional = intervieweeService.getIntervieweeByCandidateId(candidateId);

        Interviewee interviewee;
        if (!intervieweeService.getIntervieweeByCandidateId(candidateId).isPresent()) {
            interviewee = new Interviewee();
            interviewee.setCandidateId(candidateId);
            intervieweeService.save(interviewee);
        } else {
            interviewee = optional.get();
        }


        Optional<InterviewQuestion> currentQuestion = interviewQuestionService.currentQuestion(interviewee.getId());

        if (currentQuestion.isPresent())
            return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, interviewee.getId().toString())).build();


        List<InterviewQuestionSet> interviewQuestionSets = interviewQuestionSetService.findByIntervieweeId(interviewee.getId());
        for (InterviewQuestionSet set : interviewQuestionSets) {
            if (set.getQuestionset().getName().equalsIgnoreCase(questionSet))
                return ResponseEntity.badRequest().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, interviewee.getId().toString())).build();
        }

        QuestionSet questionSetObject = questionSetService.getQuestionSetByName(questionSet);
        if (questionSetObject==null)
            throw new RuntimeException("questionset should not be null");

        InterviewQuestionSet interviewQuestionSet = new InterviewQuestionSet();
        interviewQuestionSet.setInterviewee(interviewee);
        interviewQuestionSet.setQuestionset(questionSetObject);
        interviewQuestionSet.setActive(Boolean.TRUE);
        interviewQuestionSet.setTime(Instant.now());
        interviewQuestionSetService.save(interviewQuestionSet);

        advanceState(interviewee.getId());
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, interviewee.toString())).build();
    }

    private void advanceState(Long intervieweeId) {
        Optional<Interviewee> interviewee = intervieweeService.findOne(intervieweeId);
        if (!interviewee.isPresent()) {
            throw new RuntimeException("interviewee should not be null");
        }

        List<InterviewQuestionSet> interviewQuestionSetList = interviewQuestionSetService.findByIntervieweeIdAndActive(intervieweeId, Boolean.TRUE);
        if (interviewQuestionSetList.isEmpty())
            throw new RuntimeException("InterviewQuestionSet should not be null");
        if (interviewQuestionSetList.size() > 1) {
            try {
                throw new RuntimeException("InterviewQuestionSet is ambiguous, defaulting to first available");
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }

        InterviewQuestionSet interviewQuestionSet = interviewQuestionSetList.get(0);
//        List<QuestionSet> questionSetList = questionSetService.getQuestionSetByName(interviewQuestionSet.getQuestionset().getName());
        Map<QTag, Integer> qCountMap = new HashMap<>();
//        questionSetList.forEach(questionSet -> qCountMap.put(questionSet.getQtag(), questionSet.getqCount()));
        List<QuestionSetTag> questionSetTags = questionSetTagService.findByQuestionsetId(interviewQuestionSet.getQuestionset().getId());
        questionSetTags.forEach(questionSetTag -> qCountMap.put(questionSetTag.getQtag(), questionSetTag.getqCount()));
        List<InterviewQuestion> interviewQuestions = interviewQuestionService.questionsAsked(intervieweeId, interviewQuestionSet.getId());
        List<Question> questionsAsked = interviewQuestions.stream().map(InterviewQuestion::getQuestion).collect(Collectors.toList());
        questionsAsked.forEach(question -> {
            List<QuestionTag> qTags = questionTagService.getQTags(question.getId());
            qTags.forEach(questionTag -> {
                int c = qCountMap.get(questionTag.getQtag()) - 1;
                if (c == 0)
                    qCountMap.remove(questionTag.getQtag());
                else
                    qCountMap.put(questionTag.getQtag(), c);
            });
        });

        List<Question> topicQuestions = new ArrayList<>();
        do {
            QTag pickedQTag = pickQTag(qCountMap);
            if (pickedQTag == null)
                break;
            Integer weight = determineNextQuestionWeight(pickedQTag, interviewQuestions);

            while (topicQuestions.isEmpty() && weight > 0) {
                List<QuestionTag> questionTagList = getQuestionTagsForTopicWeight(pickedQTag, weight);
                topicQuestions = questionTagList.stream().map(QuestionTag::getQuestion).collect(Collectors.toList());
                topicQuestions.removeAll(questionsAsked);
                weight--;
            }
            qCountMap.remove(pickedQTag);
        } while (topicQuestions.isEmpty() && !qCountMap.isEmpty());

        if (topicQuestions.isEmpty()) {
            interviewQuestionSet.setActive(Boolean.FALSE);
            interviewQuestionSetService.save(interviewQuestionSet);
            return;
        }

        Question nextQuestion = topicQuestions.get((int) (topicQuestions.size() * Math.random()));
        InterviewQuestion newInterviewQuestion = new InterviewQuestion();
        newInterviewQuestion.setQuestion(nextQuestion);
        newInterviewQuestion.setInterviewee(interviewee.get());
        newInterviewQuestion.setInterviewqs(interviewQuestionSet);

        interviewQuestionService.save(newInterviewQuestion);
    }

    private Integer determineNextQuestionWeight(QTag qTag, List<InterviewQuestion> interviewQuestions) {

        System.out.println("Interviewee Questions : " + interviewQuestions);
        List<Float> scores = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();

        for (int i = interviewQuestions.size(); i > 0; i--) {
            InterviewQuestion interviewQuestion = interviewQuestions.get(i - 1);
            Long id = interviewQuestion.getQuestion().getId();
            List<QuestionTag> qTags = questionTagService.getQTags(id);
            if (qTags.stream().map(QuestionTag::getQtag).collect(Collectors.toList()).contains(qTag)) {
                scores.add(interviewQuestion.getScore());
                weights.add(interviewQuestion.getQuestion().getWeight());
                if (scores.size() == 2) {
                    break;
                }
            }
        }

        Integer weight = 1;

        if (weights.isEmpty())
            return weight;

        weight = weights.get(0);

        if (weights.size() == 1 || !weights.get(0).equals(weights.get(1)))
            return weight;

        if (scores.get(0) <= 5 && scores.get(1) <= 5) {
            if (weight > 1)
                weight--;
        } else if (scores.get(0) > 5 && scores.get(1) > 5)
            weight++;

        return weight;
    }


    public List<QuestionTag> getQuestionTagsForTopicWeight(QTag qTag, Integer weight) {
        List<QuestionTag> questionTags = null;
        do {
            questionTags = questionTagService.findByQtagIdAndQuestionWeight(qTag.getId(), weight);
        } while (questionTags.isEmpty() && --weight > 0);

        return questionTags;
    }

    public QTag pickQTag(Map<QTag, Integer> qCountMap) {
        if (qCountMap.isEmpty())
            return null;
        return qCountMap.keySet().stream().skip(new Random().nextInt(qCountMap.keySet().size())).findFirst().orElse(null);
    }


}
