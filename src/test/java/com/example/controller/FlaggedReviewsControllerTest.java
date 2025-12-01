package com.example.controller;

import com.example.repository.FlaggedReviewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlaggedReviewsControllerTest {

    @Mock
    FlaggedReviewsRepository repository;

    //   This test verifies that the getFlaggedReviews method returns a map containing the reviews list
    @Test
    public void getFlaggedReviews_returnsMapWithReviewsKey() {
        List<Map<String, Object>> mocked = new ArrayList<>();
        Map<String, Object> row = new HashMap<>();
        row.put("id", 1);
        row.put("user", "bob");
        mocked.add(row);

        when(repository.getFlaggedReviews()).thenReturn(mocked);

        FlaggedReviewsController controller = new FlaggedReviewsController(repository);
        Map<String, Object> resp = controller.getFlaggedReviews();

        assertTrue(resp.containsKey("reviews"));
        assertSame(mocked, resp.get("reviews"));
        verify(repository).getFlaggedReviews();
    }
}
