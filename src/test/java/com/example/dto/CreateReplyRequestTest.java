package com.example.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateReplyRequestTest {

    @Test
    void testReplyTextGetterSetter() {
        CreateReplyRequest request = new CreateReplyRequest();

        request.setReplyText("This is a test reply");

        // assert the getter returns what was set
        assertEquals("This is a test reply", request.getReplyText());
    }
}
