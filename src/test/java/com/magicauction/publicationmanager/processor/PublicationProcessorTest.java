package com.magicauction.publicationmanager.processor;

import com.magicauction.publicationmanager.entity.Card;
import com.magicauction.publicationmanager.entity.CardState;
import com.magicauction.publicationmanager.entity.MagicSet;
import com.magicauction.publicationmanager.entity.Publication;
import com.magicauction.publicationmanager.entity.User;
import com.magicauction.publicationmanager.entity.dtos.PublicationDto;
import com.magicauction.publicationmanager.entity.exceptions.CardNotFoundException;
import com.magicauction.publicationmanager.entity.exceptions.UserNotFoundException;
import com.magicauction.publicationmanager.entity.repository.PublicationRepository;
import com.magicauction.publicationmanager.entity.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicationProcessorTest {

    PublicationProcessor processor;
    @Mock CardFinderRepository finder;
    @Mock PublicationRepository pubRepository;
    @Mock UserRepository userRepository;

    @BeforeEach void init() {
        processor = new PublicationProcessor(pubRepository, userRepository, finder);
    }

    @Test void findPubById_ok(){
        long id  = 1L;
        when(pubRepository.findById(id)).thenReturn(Optional.of(pub(id)));
        Optional<PublicationDto> expected = Optional.of(pubDto(id));
        Optional<PublicationDto> actual = processor.findPubById(1L);
        assertTrue(actual.isPresent());
        assertEquals(expected, actual);
    }

    @Test void findAllPubs_ok(){
        when(pubRepository.findAll()).thenReturn(Arrays.asList(pub(1L),pub(2L),pub(3L)));
        List<PublicationDto> expected = Arrays.asList(pubDto(1L),pubDto(2L),pubDto(3L));
        List<PublicationDto> actual = processor.findAllPubs();
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
    }

    @Test void createNewPubs_ok() throws UserNotFoundException, CardNotFoundException {
        long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(user(id)));
        when(pubRepository.save(any())).thenReturn(pub(id));
        Optional<PublicationDto> expected = Optional.of(pubDto(id));
        Optional<PublicationDto> actual = processor.createNewPub(pubDto(id));
        assertTrue(actual.isPresent());
        assertEquals(expected, actual);
    }
    @Test void createNewPubs_InvalidInput(){}

    @Test void updatePub_ok() throws UserNotFoundException, CardNotFoundException {
        long id = 1L;
        when(pubRepository.save(any())).thenReturn(pub(id));
        when(pubRepository.findById(any())).thenReturn(Optional.of(pub(id)));
        Optional<PublicationDto> expected = Optional.of(pubDto(id));
        Optional<PublicationDto> actual = processor.updatePub(id, pubDto(id));
        assertTrue(actual.isPresent());
        assertEquals(expected, actual);
    }
    @Test void updatePub_InvalidInput(){}
    @Test void updatePub_PubNotFound(){}

    @Test void deletePubById_ok(){
        long id  = 1L;
        when(pubRepository.findById(id)).thenReturn(Optional.of(pub(id)));
        assertTrue(processor.deletePubById(id));
        assertFalse(processor.deletePubById(id*2));
    }
    @Test void finishPubsById_ok(){
        when(pubRepository.findAllById(anyList())).thenReturn(Arrays.asList(pub(1L),pub(2L),pub(3L)));
        when(pubRepository.save(any())).thenReturn(pub(1L));
        List<PublicationDto> expected = Arrays.asList(pubDto(1L),pubDto(2L),pubDto(3L));
        List<PublicationDto> actual = processor.finishPubsByIds(Arrays.asList(1L,2L,3L));
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
    }

    private Publication pub(long id) {
        Publication pub = new Publication();
        pub.setPublisher(user(id));
        pub.setPrice(0.21f);
        pub.setCard(card(id*2L));
        pub.setPublicationId(id);
        pub.setCardState(CardState.NEAR_MINT);
        return pub;
    }

    private Card card(long id) {
        Card c = new Card();
        c.setId(id);
        c.setScryfallId("d9caf326-9f85-4fda-88a4-7862e0f5dc49");
        c.setName("Omniscience");
        c.setFoil(true);
        c.setImgStatus("highres_scan");
        c.setImageUri("{small=https://cards.scryfall.io/small/front/d/9/d9caf326-9f85-4fda-88a4-7862e0f5dc49.jpg?1692933392, normal=https://cards.scryfall.io/normal/front/d/9/d9caf326-9f85-4fda-88a4-7862e0f5dc49.jpg?1692933392, large=https://cards.scryfall.io/large/front/d/9/d9caf326-9f85-4fda-88a4-7862e0f5dc49.jpg?1692933392, png=https://cards.scryfall.io/png/front/d/9/d9caf326-9f85-4fda-88a4-7862e0f5dc49.png?1692933392, border_crop=https://cards.scryfall.io/border_crop/front/d/9/d9caf326-9f85-4fda-88a4-7862e0f5dc49.jpg?1692933392, art_crop=https://cards.scryfall.io/art_crop/front/d/9/d9caf326-9f85-4fda-88a4-7862e0f5dc49.jpg?1692933392}\thighres_scan\t1\t2024-01-16 14:51:15.522000\tOmniscience\t{usd_foil=98.63, usd_etched=0.0, tix=0.0, eur=0.0, usd=0.0, eur_foil=0.0}");
        c.setLastModification(new Date());
        c.setMagicSet(magicSet(28L));
        c.setRelatedUri("{tcgplayer_infinite_decks=https://tcgplayer.pxf.io/c/4931599/1830156/21018?subId1=api&trafcat=infinite&u=https%3A%2F%2Finfinite.tcgplayer.com%2Fsearch%3FcontentMode%3Ddeck%26game%3Dmagic%26partner%3Dscryfall%26q%3DOmniscience, edhrec=https://edhrec.com/route/?cc=Omniscience, tcgplayer_infinite_articles=https://tcgplayer.pxf.io/c/4931599/1830156/21018?subId1=api&trafcat=infinite&u=https%3A%2F%2Finfinite.tcgplayer.com%2Fsearch%3FcontentMode%3Darticle%26game%3Dmagic%26partner%3Dscryfall%26q%3DOmniscience}");
        c.setPrices("{cardmarket=https://www.cardmarket.com/en/Magic/Products/Search?referrer=scryfall&searchString=Omniscience&utm_campaign=card_prices&utm_medium=text&utm_source=scryfall, cardhoarder=https://www.cardhoarder.com/cards?affiliate_id=scryfall&data%5Bsearch%5D=Omniscience&ref=card-profile&utm_campaign=affiliate&utm_medium=card&utm_source=scryfall, tcgplayer=https://tcgplayer.pxf.io/c/4931599/1830156/21018?subId1=api&u=https%3A%2F%2Fwww.tcgplayer.com%2Fproduct%2F512693%3Fpage%3D1}");
        c.setPurchaseUri("{cardmarket=https://www.cardmarket.com/en/Magic/Products/Search?referrer=scryfall&searchString=Omniscience&utm_campaign=card_prices&utm_medium=text&utm_source=scryfall, cardhoarder=https://www.cardhoarder.com/cards?affiliate_id=scryfall&data%5Bsearch%5D=Omniscience&ref=card-profile&utm_campaign=affiliate&utm_medium=card&utm_source=scryfall, tcgplayer=https://tcgplayer.pxf.io/c/4931599/1830156/21018?subId1=api&u=https%3A%2F%2Fwww.tcgplayer.com%2Fproduct%2F512693%3Fpage%3D1}");
        return c;
    }

    private MagicSet magicSet(long id) {
        MagicSet set = new MagicSet();
        set.setId(id);
        set.setCardCount(103);
        set.setName("Wilds of Eldraine: Enchanting Tales");
        set.setScryfallId("d1eba841-452b-4f81-88c9-b32973ac8176");
        set.setCode("wot");
        set.setParentSetCode("wot");
        set.setDigital(false);
        set.setSearchUri("https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Awot&unique=prints");
        set.setReleasedAt("2023-09-08");
        set.setSetType("masterpiece");
        return set;
    }

    private PublicationDto pubDto(long id) {
        PublicationDto pub = new PublicationDto(
                id,
                1L,
                "Omniscience",
                "NM",
                0.21f
                );
        return pub;
    }

    private User user(long l) {
        User u = new User();
        u.setUserId(l);
        u.setName("name "+l);
        u.setTradeArea("trade area "+l);
        return u;
    }


}