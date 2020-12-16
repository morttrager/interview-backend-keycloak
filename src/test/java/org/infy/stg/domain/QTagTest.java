package org.infy.stg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.infy.stg.web.rest.TestUtil;

public class QTagTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QTag.class);
        QTag qTag1 = new QTag();
        qTag1.setId(1L);
        QTag qTag2 = new QTag();
        qTag2.setId(qTag1.getId());
        assertThat(qTag1).isEqualTo(qTag2);
        qTag2.setId(2L);
        assertThat(qTag1).isNotEqualTo(qTag2);
        qTag1.setId(null);
        assertThat(qTag1).isNotEqualTo(qTag2);
    }
}
