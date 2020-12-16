package org.infy.stg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.infy.stg.web.rest.TestUtil;

public class QuestionSetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionSet.class);
        QuestionSet questionSet1 = new QuestionSet();
        questionSet1.setId(1L);
        QuestionSet questionSet2 = new QuestionSet();
        questionSet2.setId(questionSet1.getId());
        assertThat(questionSet1).isEqualTo(questionSet2);
        questionSet2.setId(2L);
        assertThat(questionSet1).isNotEqualTo(questionSet2);
        questionSet1.setId(null);
        assertThat(questionSet1).isNotEqualTo(questionSet2);
    }
}
