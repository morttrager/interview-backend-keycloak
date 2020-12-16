package org.infy.stg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.infy.stg.web.rest.TestUtil;

public class IntervieweeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Interviewee.class);
        Interviewee interviewee1 = new Interviewee();
        interviewee1.setId(1L);
        Interviewee interviewee2 = new Interviewee();
        interviewee2.setId(interviewee1.getId());
        assertThat(interviewee1).isEqualTo(interviewee2);
        interviewee2.setId(2L);
        assertThat(interviewee1).isNotEqualTo(interviewee2);
        interviewee1.setId(null);
        assertThat(interviewee1).isNotEqualTo(interviewee2);
    }
}
