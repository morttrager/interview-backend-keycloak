package org.infy.stg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.infy.stg.web.rest.TestUtil;

public class InterviewQuestionSetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterviewQuestionSet.class);
        InterviewQuestionSet interviewQuestionSet1 = new InterviewQuestionSet();
        interviewQuestionSet1.setId(1L);
        InterviewQuestionSet interviewQuestionSet2 = new InterviewQuestionSet();
        interviewQuestionSet2.setId(interviewQuestionSet1.getId());
        assertThat(interviewQuestionSet1).isEqualTo(interviewQuestionSet2);
        interviewQuestionSet2.setId(2L);
        assertThat(interviewQuestionSet1).isNotEqualTo(interviewQuestionSet2);
        interviewQuestionSet1.setId(null);
        assertThat(interviewQuestionSet1).isNotEqualTo(interviewQuestionSet2);
    }
}
