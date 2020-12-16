package org.infy.stg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.infy.stg.web.rest.TestUtil;

public class InterviewQuestionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterviewQuestion.class);
        InterviewQuestion interviewQuestion1 = new InterviewQuestion();
        interviewQuestion1.setId(1L);
        InterviewQuestion interviewQuestion2 = new InterviewQuestion();
        interviewQuestion2.setId(interviewQuestion1.getId());
        assertThat(interviewQuestion1).isEqualTo(interviewQuestion2);
        interviewQuestion2.setId(2L);
        assertThat(interviewQuestion1).isNotEqualTo(interviewQuestion2);
        interviewQuestion1.setId(null);
        assertThat(interviewQuestion1).isNotEqualTo(interviewQuestion2);
    }
}
