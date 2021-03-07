package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private static final int NUM_INITIAL_WHITE_CHIPS = 20;
    private static final int NUM_INITIAL_BLUE_CHIPS = 10;
    private static final int NUM_INITIAL_RED_CHIPS = 5;
    private static final int NUM_INITIAL_GOLD_CHIPS = 1;
    private final Card D3 = new Card(3, "diamond");
    private final Card C3 = new Card(3, "clubs");
    private final Card H3 = new Card(3, "heart");
    private final Card S3 = new Card(3, "spade");
    private final Card H1 = new Card(1, "heart");
    private final Card H13 = new Card(13, "heart");
    private final Card H12 = new Card(12, "heart");
    private final Card H11 = new Card(11, "heart");
    private final Card H10 = new Card(10, "heart");
    private final Card H9 = new Card(9, "heart");
    private final Card D10 = new Card(10, "diamond");

    private Player player;

    @BeforeEach
    public void runBefore() {
        List<Card> initialCards = new ArrayList<>();
        initialCards.addAll(new ArrayList<>(Arrays.asList(D3, C3, H3, S3, H1, H13, H12, H11, H10, H9, D10)));
        player = new Player("player1", initialCards, NUM_INITIAL_WHITE_CHIPS, NUM_INITIAL_BLUE_CHIPS,
                NUM_INITIAL_RED_CHIPS, NUM_INITIAL_GOLD_CHIPS);
    }

    @Test
    public void testTakeATurn() {
        player.takeATurn(new Hand(new ArrayList<>(Arrays.asList(D3, C3))));
        assertEquals(9, player.getNumCards());
        assertFalse(player.getCards().contains(D3));
        assertFalse(player.getCards().contains(C3));
    }

    @Test
    public void testCollectChipsOneChip() {
        List<Chips> chipsList = new ArrayList<>();
        chipsList.add(new Chips("white"));
        player.collectChips(chipsList);
        assertEquals(37, player.getDrawer().getSize());
        assertEquals(21, player.getDrawer().getNumWhiteChips());
    }

    @Test
    public void testCollectChipsMultiple() {
        List<Chips> chipsList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            chipsList.add(new Chips("red"));
        }
        player.collectChips(chipsList);
        assertEquals(46, player.getDrawer().getSize());
        assertEquals(15, player.getDrawer().getNumRedChips());
    }

    @Test
    public void testPayChipsGold() {
        List<Chips> chipsLostExact = player.payChips(Chips.GOLD_CHIP_VALUE);
        assertEquals(1,chipsLostExact.size());
        assertEquals(35, player.getDrawer().getSize());
        assertEquals(0, player.getDrawer().getNumGoldChips());
        player.collectChips(new ArrayList<>(Arrays.asList(new Chips("gold"))));
        List<Chips> chipsLostOver = player.payChips(Chips.GOLD_CHIP_VALUE+1);
        assertEquals(2,chipsLostOver.size());
        assertEquals(34, player.getDrawer().getSize());
        assertEquals(0, player.getDrawer().getNumGoldChips());
        assertEquals(19, player.getDrawer().getNumWhiteChips());
    }

    @Test
    public void testPayChipsRed() {
        List<Chips> chipsLostExact = player.payChips(Chips.RED_CHIP_VALUE);
        assertEquals(1,chipsLostExact.size());
        assertEquals(35, player.getDrawer().getSize());
        assertEquals(4, player.getDrawer().getNumRedChips());
        player.collectChips(new ArrayList<>(Arrays.asList(new Chips("red"))));
        List<Chips> chipsLostOver = player.payChips(Chips.RED_CHIP_VALUE+1);
        assertEquals(2,chipsLostOver.size());
        assertEquals(34, player.getDrawer().getSize());
        assertEquals(4, player.getDrawer().getNumRedChips());
        assertEquals(19, player.getDrawer().getNumWhiteChips());
    }

    @Test
    public void testPayChipsBlue() {
        List<Chips> chipsLostExact = player.payChips(Chips.BLUE_CHIP_VALUE);
        assertEquals(1,chipsLostExact.size());
        assertEquals(35, player.getDrawer().getSize());
        assertEquals(9, player.getDrawer().getNumBlueChips());
        player.collectChips(new ArrayList<>(Arrays.asList(new Chips("blue"))));
        List<Chips> chipsLostOver = player.payChips(Chips.BLUE_CHIP_VALUE+1);
        assertEquals(2,chipsLostOver.size());
        assertEquals(34, player.getDrawer().getSize());
        assertEquals(9, player.getDrawer().getNumBlueChips());
        assertEquals(19, player.getDrawer().getNumWhiteChips());
    }

    @Test
    public void testPayChipsWhite() {
        List<Chips> chipsLostExact = player.payChips(Chips.WHITE_CHIP_VALUE);
        assertEquals(1,chipsLostExact.size());
        assertEquals(35, player.getDrawer().getSize());
        assertEquals(19, player.getDrawer().getNumWhiteChips());
        player.collectChips(new ArrayList<>(Arrays.asList(new Chips("white"))));
        List<Chips> chipsLostOver = player.payChips(Chips.WHITE_CHIP_VALUE+1);
        assertEquals(2,chipsLostOver.size());
        assertEquals(34, player.getDrawer().getSize());
        assertEquals(18, player.getDrawer().getNumWhiteChips());
    }

    @Test
    public void testGetName() {
        assertEquals("player1", player.getName());
    }
}
