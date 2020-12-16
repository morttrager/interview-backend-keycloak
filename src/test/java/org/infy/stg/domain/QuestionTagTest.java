package org.infy.stg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.infy.stg.web.rest.TestUtil;

public class QuestionTagTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionTag.class);
        QuestionTag questionTag1 = new QuestionTag();
        questionTag1.setId(1L);
        QuestionTag questionTag2 = new QuestionTag();
        questionTag2.setId(questionTag1.getId());
        assertThat(questionTag1).isEqualTo(questionTag2);
        questionTag2.setId(2L);
        assertThat(questionTag1).isNotEqualTo(questionTag2);
        questionTag1.setId(null);
        assertThat(questionTag1).isNotEqualTo(questionTag2);
    }
}
