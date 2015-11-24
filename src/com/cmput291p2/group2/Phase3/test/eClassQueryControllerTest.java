package com.cmput291p2.group2.Phase3.test;

import com.cmput291p2.group2.Phase3.QueryController;
import com.cmput291p2.group2.Phase3.QueryEngine;
import com.cmput291p2.group2.common.Review;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class eClassQueryControllerTest {

    QueryController qc;

    @Before
    public void setup() {
        qc = new QueryController(new QueryEngine("rw.idx", "pt.idx", "rt.idx", "sc.idx"));
    }

    @Test
    public void test1() {
        String query = "p:camera";
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() == 0);
    }

    @Test
    public void test2() {
        String query = "r:great";
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() == 314); // as per eClass forum reply
    }

    @Test
    public void test3() {
        // todo
    }

    @Test
    public void test4() {
        // modified as per eClass forum reply: removed 53
        String query = "cam%";
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);

        Set<Integer> expectedReviewIds = new HashSet<Integer>(){{
            add(new Integer(1));
            add(new Integer(17));
            add(new Integer(25));
            add(new Integer(47));
            add(new Integer(73));
            add(new Integer(81));
            add(new Integer(93));
            add(new Integer(109));
            add(new Integer(131));
            add(new Integer(161));
            add(new Integer(164));
            add(new Integer(165));
            add(new Integer(171));
            add(new Integer(173));
            add(new Integer(178));
            add(new Integer(179));
            add(new Integer(181));
            add(new Integer(182));
            add(new Integer(184));
            add(new Integer(185));
            add(new Integer(188));
            add(new Integer(204));
            add(new Integer(207));
            add(new Integer(219));
            add(new Integer(220));
            add(new Integer(227));
            add(new Integer(240));
            add(new Integer(249));
            add(new Integer(300));
            add(new Integer(303));
            add(new Integer(348));
            add(new Integer(370));
            add(new Integer(389));
            add(new Integer(390));
            add(new Integer(412));
            add(new Integer(414));
            add(new Integer(471));
            add(new Integer(539));
            add(new Integer(552));
            add(new Integer(559));
            add(new Integer(567));
            add(new Integer(569));
            add(new Integer(571));
            add(new Integer(602));
            add(new Integer(620));
            add(new Integer(637));
            add(new Integer(659));
            add(new Integer(698));
            add(new Integer(709));
            add(new Integer(717));
            add(new Integer(718));
            add(new Integer(720));
            add(new Integer(722));
            add(new Integer(761));
            add(new Integer(766));
            add(new Integer(767));
            add(new Integer(781));
            add(new Integer(799));
            add(new Integer(806));
            add(new Integer(818));
            add(new Integer(835));
            add(new Integer(848));
            add(new Integer(849));
            add(new Integer(850));
            add(new Integer(851));
            add(new Integer(852));
            add(new Integer(853));
            add(new Integer(854));
            add(new Integer(855));
            add(new Integer(856));
            add(new Integer(857));
            add(new Integer(858));
            add(new Integer(859));
            add(new Integer(860));
            add(new Integer(861));
            add(new Integer(862));
            add(new Integer(863));
            add(new Integer(864));
            add(new Integer(869));
            add(new Integer(872));
            add(new Integer(886));
            add(new Integer(897));
            add(new Integer(906));
            add(new Integer(934));
            add(new Integer(982));
            add(new Integer(996));
            add(new Integer(998));

        }};
        for (Review review : reviews) {
            assertTrue(expectedReviewIds.contains(review.getReviewId()));
            expectedReviewIds.remove(review.getReviewId());
        }
        assertTrue(expectedReviewIds.isEmpty());
    }

    @Test
    public void test5() {
        // todo
    }

    @Test
    public void test6() {
        String query = "rscore > 4";
        Collection<Review> reviews = qc.executeQuery(query);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);

        Set<Integer> expectedReviewIds = new HashSet<Integer>() {{
            add(new Integer(2));
            add(new Integer(5));
            add(new Integer(6));
            add(new Integer(7));
            add(new Integer(8));
            add(new Integer(9));
            add(new Integer(10));
            add(new Integer(11));
            add(new Integer(12));
            add(new Integer(14));
            add(new Integer(15));
            add(new Integer(16));
            add(new Integer(17));
            add(new Integer(18));
            add(new Integer(19));
            add(new Integer(20));
            add(new Integer(22));
            add(new Integer(23));
            add(new Integer(24));
            add(new Integer(25));
            add(new Integer(26));
            add(new Integer(27));
            add(new Integer(28));
            add(new Integer(29));
            add(new Integer(30));
            add(new Integer(31));
            add(new Integer(32));
            add(new Integer(33));
            add(new Integer(34));
            add(new Integer(35));
            add(new Integer(36));
            add(new Integer(37));
            add(new Integer(38));
            add(new Integer(39));
            add(new Integer(40));
            add(new Integer(41));
            add(new Integer(42));
            add(new Integer(43));
            add(new Integer(44));
            add(new Integer(45));
            add(new Integer(46));
            add(new Integer(47));
            add(new Integer(48));
            add(new Integer(49));
            add(new Integer(50));
            add(new Integer(51));
            add(new Integer(52));
            add(new Integer(53));
            add(new Integer(54));
            add(new Integer(55));
            add(new Integer(56));
            add(new Integer(57));
            add(new Integer(58));
            add(new Integer(59));
            add(new Integer(60));
            add(new Integer(61));
            add(new Integer(62));
            add(new Integer(63));
            add(new Integer(67));
            add(new Integer(68));
            add(new Integer(69));
            add(new Integer(71));
            add(new Integer(72));
            add(new Integer(73));
            add(new Integer(74));
            add(new Integer(75));
            add(new Integer(76));
            add(new Integer(77));
            add(new Integer(81));
            add(new Integer(82));
            add(new Integer(83));
            add(new Integer(84));
            add(new Integer(85));
            add(new Integer(86));
            add(new Integer(87));
            add(new Integer(88));
            add(new Integer(89));
            add(new Integer(92));
            add(new Integer(93));
            add(new Integer(94));
            add(new Integer(95));
            add(new Integer(97));
            add(new Integer(99));
            add(new Integer(100));
            add(new Integer(107));
            add(new Integer(108));
            add(new Integer(110));
            add(new Integer(111));
            add(new Integer(112));
            add(new Integer(113));
            add(new Integer(114));
            add(new Integer(115));
            add(new Integer(116));
            add(new Integer(118));
            add(new Integer(119));
            add(new Integer(120));
            add(new Integer(122));
            add(new Integer(123));
            add(new Integer(124));
            add(new Integer(125));
            add(new Integer(126));
            add(new Integer(129));
            add(new Integer(130));
            add(new Integer(131));
            add(new Integer(132));
            add(new Integer(133));
            add(new Integer(134));
            add(new Integer(138));
            add(new Integer(139));
            add(new Integer(140));
            add(new Integer(143));
            add(new Integer(144));
            add(new Integer(146));
            add(new Integer(147));
            add(new Integer(150));
            add(new Integer(152));
            add(new Integer(154));
            add(new Integer(158));
            add(new Integer(161));
            add(new Integer(163));
            add(new Integer(164));
            add(new Integer(166));
            add(new Integer(167));
            add(new Integer(169));
            add(new Integer(171));
            add(new Integer(172));
            add(new Integer(173));
            add(new Integer(175));
            add(new Integer(179));
            add(new Integer(180));
            add(new Integer(181));
            add(new Integer(182));
            add(new Integer(183));
            add(new Integer(184));
            add(new Integer(185));
            add(new Integer(186));
            add(new Integer(187));
            add(new Integer(188));
            add(new Integer(189));
            add(new Integer(190));
            add(new Integer(192));
            add(new Integer(193));
            add(new Integer(195));
            add(new Integer(196));
            add(new Integer(197));
            add(new Integer(198));
            add(new Integer(199));
            add(new Integer(200));
            add(new Integer(201));
            add(new Integer(206));
            add(new Integer(210));
            add(new Integer(215));
            add(new Integer(219));
            add(new Integer(222));
            add(new Integer(224));
            add(new Integer(230));
            add(new Integer(231));
            add(new Integer(233));
            add(new Integer(235));
            add(new Integer(236));
            add(new Integer(239));
            add(new Integer(241));
            add(new Integer(242));
            add(new Integer(244));
            add(new Integer(246));
            add(new Integer(247));
            add(new Integer(248));
            add(new Integer(250));
            add(new Integer(251));
            add(new Integer(252));
            add(new Integer(253));
            add(new Integer(254));
            add(new Integer(256));
            add(new Integer(258));
            add(new Integer(259));
            add(new Integer(261));
            add(new Integer(262));
            add(new Integer(263));
            add(new Integer(264));
            add(new Integer(265));
            add(new Integer(266));
            add(new Integer(269));
            add(new Integer(270));
            add(new Integer(272));
            add(new Integer(274));
            add(new Integer(275));
            add(new Integer(278));
            add(new Integer(279));
            add(new Integer(280));
            add(new Integer(281));
            add(new Integer(282));
            add(new Integer(283));
            add(new Integer(284));
            add(new Integer(285));
            add(new Integer(286));
            add(new Integer(287));
            add(new Integer(294));
            add(new Integer(296));
            add(new Integer(297));
            add(new Integer(298));
            add(new Integer(299));
            add(new Integer(300));
            add(new Integer(301));
            add(new Integer(302));
            add(new Integer(303));
            add(new Integer(304));
            add(new Integer(306));
            add(new Integer(307));
            add(new Integer(308));
            add(new Integer(310));
            add(new Integer(311));
            add(new Integer(312));
            add(new Integer(313));
            add(new Integer(314));
            add(new Integer(316));
            add(new Integer(319));
            add(new Integer(321));
            add(new Integer(323));
            add(new Integer(324));
            add(new Integer(325));
            add(new Integer(327));
            add(new Integer(328));
            add(new Integer(329));
            add(new Integer(331));
            add(new Integer(332));
            add(new Integer(333));
            add(new Integer(334));
            add(new Integer(340));
            add(new Integer(342));
            add(new Integer(343));
            add(new Integer(344));
            add(new Integer(345));
            add(new Integer(347));
            add(new Integer(348));
            add(new Integer(349));
            add(new Integer(350));
            add(new Integer(351));
            add(new Integer(353));
            add(new Integer(354));
            add(new Integer(356));
            add(new Integer(357));
            add(new Integer(358));
            add(new Integer(359));
            add(new Integer(361));
            add(new Integer(364));
            add(new Integer(367));
            add(new Integer(368));
            add(new Integer(369));
            add(new Integer(373));
            add(new Integer(374));
            add(new Integer(376));
            add(new Integer(377));
            add(new Integer(379));
            add(new Integer(380));
            add(new Integer(382));
            add(new Integer(385));
            add(new Integer(386));
            add(new Integer(387));
            add(new Integer(388));
            add(new Integer(391));
            add(new Integer(393));
            add(new Integer(394));
            add(new Integer(395));
            add(new Integer(396));
            add(new Integer(397));
            add(new Integer(399));
            add(new Integer(401));
            add(new Integer(404));
            add(new Integer(406));
            add(new Integer(410));
            add(new Integer(411));
            add(new Integer(413));
            add(new Integer(414));
            add(new Integer(415));
            add(new Integer(416));
            add(new Integer(417));
            add(new Integer(418));
            add(new Integer(419));
            add(new Integer(421));
            add(new Integer(422));
            add(new Integer(424));
            add(new Integer(425));
            add(new Integer(426));
            add(new Integer(429));
            add(new Integer(431));
            add(new Integer(434));
            add(new Integer(435));
            add(new Integer(436));
            add(new Integer(439));
            add(new Integer(440));
            add(new Integer(441));
            add(new Integer(442));
            add(new Integer(443));
            add(new Integer(446));
            add(new Integer(449));
            add(new Integer(450));
            add(new Integer(452));
            add(new Integer(456));
            add(new Integer(457));
            add(new Integer(458));
            add(new Integer(459));
            add(new Integer(460));
            add(new Integer(461));
            add(new Integer(462));
            add(new Integer(465));
            add(new Integer(466));
            add(new Integer(467));
            add(new Integer(468));
            add(new Integer(469));
            add(new Integer(471));
            add(new Integer(474));
            add(new Integer(476));
            add(new Integer(480));
            add(new Integer(482));
            add(new Integer(484));
            add(new Integer(489));
            add(new Integer(491));
            add(new Integer(493));
            add(new Integer(494));
            add(new Integer(495));
            add(new Integer(496));
            add(new Integer(500));
            add(new Integer(501));
            add(new Integer(502));
            add(new Integer(503));
            add(new Integer(504));
            add(new Integer(505));
            add(new Integer(506));
            add(new Integer(507));
            add(new Integer(508));
            add(new Integer(509));
            add(new Integer(510));
            add(new Integer(511));
            add(new Integer(512));
            add(new Integer(513));
            add(new Integer(514));
            add(new Integer(517));
            add(new Integer(519));
            add(new Integer(520));
            add(new Integer(521));
            add(new Integer(524));
            add(new Integer(525));
            add(new Integer(526));
            add(new Integer(527));
            add(new Integer(528));
            add(new Integer(529));
            add(new Integer(530));
            add(new Integer(532));
            add(new Integer(536));
            add(new Integer(542));
            add(new Integer(544));
            add(new Integer(546));
            add(new Integer(547));
            add(new Integer(548));
            add(new Integer(549));
            add(new Integer(554));
            add(new Integer(555));
            add(new Integer(556));
            add(new Integer(557));
            add(new Integer(559));
            add(new Integer(560));
            add(new Integer(561));
            add(new Integer(562));
            add(new Integer(563));
            add(new Integer(565));
            add(new Integer(567));
            add(new Integer(568));
            add(new Integer(569));
            add(new Integer(570));
            add(new Integer(572));
            add(new Integer(573));
            add(new Integer(574));
            add(new Integer(575));
            add(new Integer(576));
            add(new Integer(577));
            add(new Integer(578));
            add(new Integer(579));
            add(new Integer(586));
            add(new Integer(587));
            add(new Integer(588));
            add(new Integer(589));
            add(new Integer(591));
            add(new Integer(592));
            add(new Integer(594));
            add(new Integer(595));
            add(new Integer(596));
            add(new Integer(597));
            add(new Integer(598));
            add(new Integer(600));
            add(new Integer(603));
            add(new Integer(610));
            add(new Integer(611));
            add(new Integer(614));
            add(new Integer(615));
            add(new Integer(616));
            add(new Integer(617));
            add(new Integer(618));
            add(new Integer(623));
            add(new Integer(628));
            add(new Integer(630));
            add(new Integer(631));
            add(new Integer(632));
            add(new Integer(633));
            add(new Integer(635));
            add(new Integer(636));
            add(new Integer(637));
            add(new Integer(638));
            add(new Integer(639));
            add(new Integer(640));
            add(new Integer(641));
            add(new Integer(642));
            add(new Integer(644));
            add(new Integer(645));
            add(new Integer(646));
            add(new Integer(647));
            add(new Integer(648));
            add(new Integer(649));
            add(new Integer(653));
            add(new Integer(654));
            add(new Integer(655));
            add(new Integer(657));
            add(new Integer(662));
            add(new Integer(663));
            add(new Integer(664));
            add(new Integer(665));
            add(new Integer(666));
            add(new Integer(668));
            add(new Integer(669));
            add(new Integer(670));
            add(new Integer(671));
            add(new Integer(672));
            add(new Integer(673));
            add(new Integer(674));
            add(new Integer(675));
            add(new Integer(678));
            add(new Integer(679));
            add(new Integer(680));
            add(new Integer(682));
            add(new Integer(684));
            add(new Integer(686));
            add(new Integer(688));
            add(new Integer(689));
            add(new Integer(690));
            add(new Integer(691));
            add(new Integer(693));
            add(new Integer(694));
            add(new Integer(696));
            add(new Integer(698));
            add(new Integer(701));
            add(new Integer(702));
            add(new Integer(703));
            add(new Integer(704));
            add(new Integer(706));
            add(new Integer(707));
            add(new Integer(709));
            add(new Integer(710));
            add(new Integer(712));
            add(new Integer(713));
            add(new Integer(714));
            add(new Integer(715));
            add(new Integer(716));
            add(new Integer(717));
            add(new Integer(718));
            add(new Integer(719));
            add(new Integer(720));
            add(new Integer(721));
            add(new Integer(722));
            add(new Integer(723));
            add(new Integer(724));
            add(new Integer(725));
            add(new Integer(727));
            add(new Integer(728));
            add(new Integer(729));
            add(new Integer(730));
            add(new Integer(732));
            add(new Integer(734));
            add(new Integer(735));
            add(new Integer(736));
            add(new Integer(737));
            add(new Integer(739));
            add(new Integer(740));
            add(new Integer(741));
            add(new Integer(742));
            add(new Integer(747));
            add(new Integer(748));
            add(new Integer(749));
            add(new Integer(750));
            add(new Integer(751));
            add(new Integer(752));
            add(new Integer(753));
            add(new Integer(758));
            add(new Integer(759));
            add(new Integer(760));
            add(new Integer(761));
            add(new Integer(765));
            add(new Integer(766));
            add(new Integer(768));
            add(new Integer(771));
            add(new Integer(772));
            add(new Integer(773));
            add(new Integer(774));
            add(new Integer(775));
            add(new Integer(776));
            add(new Integer(777));
            add(new Integer(778));
            add(new Integer(779));
            add(new Integer(780));
            add(new Integer(781));
            add(new Integer(782));
            add(new Integer(783));
            add(new Integer(784));
            add(new Integer(785));
            add(new Integer(786));
            add(new Integer(787));
            add(new Integer(788));
            add(new Integer(789));
            add(new Integer(790));
            add(new Integer(791));
            add(new Integer(792));
            add(new Integer(794));
            add(new Integer(795));
            add(new Integer(796));
            add(new Integer(797));
            add(new Integer(799));
            add(new Integer(801));
            add(new Integer(802));
            add(new Integer(804));
            add(new Integer(807));
            add(new Integer(810));
            add(new Integer(811));
            add(new Integer(816));
            add(new Integer(817));
            add(new Integer(818));
            add(new Integer(819));
            add(new Integer(822));
            add(new Integer(823));
            add(new Integer(824));
            add(new Integer(825));
            add(new Integer(826));
            add(new Integer(827));
            add(new Integer(828));
            add(new Integer(838));
            add(new Integer(846));
            add(new Integer(847));
            add(new Integer(850));
            add(new Integer(851));
            add(new Integer(852));
            add(new Integer(853));
            add(new Integer(855));
            add(new Integer(856));
            add(new Integer(857));
            add(new Integer(860));
            add(new Integer(861));
            add(new Integer(863));
            add(new Integer(868));
            add(new Integer(873));
            add(new Integer(874));
            add(new Integer(875));
            add(new Integer(876));
            add(new Integer(877));
            add(new Integer(879));
            add(new Integer(883));
            add(new Integer(884));
            add(new Integer(885));
            add(new Integer(886));
            add(new Integer(887));
            add(new Integer(888));
            add(new Integer(889));
            add(new Integer(891));
            add(new Integer(892));
            add(new Integer(893));
            add(new Integer(894));
            add(new Integer(899));
            add(new Integer(901));
            add(new Integer(903));
            add(new Integer(904));
            add(new Integer(905));
            add(new Integer(907));
            add(new Integer(908));
            add(new Integer(909));
            add(new Integer(910));
            add(new Integer(911));
            add(new Integer(913));
            add(new Integer(914));
            add(new Integer(918));
            add(new Integer(919));
            add(new Integer(920));
            add(new Integer(923));
            add(new Integer(925));
            add(new Integer(926));
            add(new Integer(928));
            add(new Integer(929));
            add(new Integer(932));
            add(new Integer(933));
            add(new Integer(934));
            add(new Integer(935));
            add(new Integer(936));
            add(new Integer(937));
            add(new Integer(938));
            add(new Integer(939));
            add(new Integer(940));
            add(new Integer(941));
            add(new Integer(944));
            add(new Integer(945));
            add(new Integer(947));
            add(new Integer(951));
            add(new Integer(952));
            add(new Integer(954));
            add(new Integer(958));
            add(new Integer(962));
            add(new Integer(963));
            add(new Integer(964));
            add(new Integer(968));
            add(new Integer(969));
            add(new Integer(970));
            add(new Integer(975));
            add(new Integer(976));
            add(new Integer(977));
            add(new Integer(978));
            add(new Integer(979));
            add(new Integer(980));
            add(new Integer(981));
            add(new Integer(982));
            add(new Integer(983));
            add(new Integer(985));
            add(new Integer(986));
            add(new Integer(987));
            add(new Integer(988));
            add(new Integer(989));
            add(new Integer(990));
            add(new Integer(992));
            add(new Integer(993));
            add(new Integer(994));
            add(new Integer(995));
            add(new Integer(999));
            add(new Integer(1000));
        }};
        for (Review review : reviews) {
            assertTrue(expectedReviewIds.contains(review.getReviewId()));
            expectedReviewIds.remove(review.getReviewId());
        }
        assertTrue(expectedReviewIds.isEmpty());
    }

    @Test
    public void test7() {
        // todo
    }

    @Test
    public void test8() {
        // todo
    }

    @Test
    public void test9() throws ParseException {
        // todo
    }

    @Test
    public void test10() throws ParseException {
        // todo
    }
}