package org.infy.stg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.infy.stg.web.rest.TestUtil;

public class QuestionSetTagTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionSetTag.class);
        QuestionSetTag questionSetTag1 = new QuestionSetTag();
        questionSetTag1.setId(1L);
        QuestionSetTag questionSetTag2 = new QuestionSetTag();
        questionSetTag2.setId(questionSetTag1.getId());
        assertThat(questionSetTag1).isEqualTo(questionSetTag2);
        questionSetTag2.setId(2L);
        assertThat(questionSetTag1).isNotEqualTo(questionSetTag2);
        questionSetTag1.setId(null);
        assertThat(questionSetTag1).isNotEqualTo(questionSetTag2);
    }
}
