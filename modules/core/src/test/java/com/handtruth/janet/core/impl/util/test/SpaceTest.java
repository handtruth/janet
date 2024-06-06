package com.handtruth.janet.core.impl.util.test;

import com.handtruth.janet.core.impl.util.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpaceTest {

    private Space<Integer> space;

    @BeforeEach
    void initializeSpace() {
        space = Space.create(x -> x + 1);
    }

    @Test
    void addOneElement() {
        assertTrue(space.isEmpty());
        assertTrue(space.add(42));
        assertFalse(space.isEmpty());
        assertEquals("[42]", space.toString());
    }

    @Test
    void addReverseRange() {
        assertTrue(space.addRange(42, 23));
        assertEquals(1, space.rangeCount());
        assertFalse(space.contains(22));
        assertFalse(space.contains(43));
        for (int i = 23; i <= 42; ++i) {
            assertTrue(space.contains(i));
        }
        assertEquals("[23-42]", space.toString());
    }

    @Test
    void addSubsetRange() {
        assertTrue(space.addRange(23, 42));
        assertFalse(space.add(30));
        assertEquals(1, space.rangeCount());
        assertEquals("[23-42]", space.toString());
    }

    @Test
    void addSubsetRangeExact() {
        assertTrue(space.addRange(23, 42));
        assertFalse(space.addRange(23, 42));
        assertEquals(1, space.rangeCount());
        assertEquals("[23-42]", space.toString());
    }

    @Test
    void fillSpaceBetweenRanges() {
        assertTrue(space.addRange(23, 42));
        assertTrue(space.addRange(69, 420));
        assertTrue(space.addRange(45, 45));
        assertEquals("[23-42, 45, 69-420]", space.toString());
        assertEquals(3, space.rangeCount());
        assertTrue(space.addRange(40, 120));
        assertEquals(1, space.rangeCount());
        assertEquals("[23-420]", space.toString());
    }

    @Test
    void fillSpaceBetweenRangesExactly() {
        assertTrue(space.addRange(23, 42));
        assertTrue(space.addRange(69, 420));
        assertEquals("[23-42, 69-420]", space.toString());
        assertEquals(2, space.rangeCount());
        assertTrue(space.addRange(43, 68));
        assertEquals("[23-420]", space.toString());
        assertEquals(1, space.rangeCount());
    }

    @Test
    void extendRangeToLeft() {
        assertTrue(space.add(1));
        assertTrue(space.addRange(23, 42));
        assertTrue(space.addRange(10, 30));
        assertEquals("[1, 10-42]", space.toString());
    }

    @Test
    void extendRangeToLeftExact() {
        assertTrue(space.add(1));
        assertTrue(space.addRange(23, 42));
        assertTrue(space.addRange(10, 22));
        assertEquals("[1, 10-42]", space.toString());
    }

    @Test
    void extendRangeToRight() {
        assertTrue(space.addRange(23, 42));
        assertTrue(space.addRange(71, 420));
        assertTrue(space.addRange(30, 69));
        assertEquals("[23-69, 71-420]", space.toString());
    }

    @Test
    void extendRangeToRightExact() {
        assertTrue(space.addRange(23, 42));
        assertTrue(space.addRange(43, 69));
        assertEquals("[23-69]", space.toString());
    }

    @Test
    void reverseAdd() {
        assertTrue(space.addRange(42, 23));
        assertFalse(space.addRange(40, 30));
    }

    @Test
    void absorbRange() {
        assertTrue(space.addRange(23, 42));
        assertTrue(space.addRange(69, 420));
        assertTrue(space.addRange(500, 600));
        assertTrue(space.addRange(0, 10));
        assertEquals("[0-10, 23-42, 69-420, 500-600]", space.toString());
        assertTrue(space.addRange(22, 421));
        assertEquals("[0-10, 22-421, 500-600]", space.toString());
    }

    @Test
    void emptySpaceToString() {
        assertEquals("[]", space.toString());
    }
}
