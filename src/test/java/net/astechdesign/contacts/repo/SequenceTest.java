package net.astechdesign.contacts.repo;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SequenceTest {

    @Test
    public void constructor() throws Exception {
        Sequence seq = new Sequence("contact");
        assertThat(seq.toString(), is(Sequence.SEQUENCE + "CONTACT_SEQ"));
    }
}