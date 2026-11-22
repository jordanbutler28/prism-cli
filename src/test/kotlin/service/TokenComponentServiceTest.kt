package service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TokenComponentServiceTest {
    private lateinit var emptyService: TokenComponentService
    private lateinit var populatedService: TokenComponentService

    @BeforeEach
    fun setUp() {
        emptyService = TokenComponentService()
        populatedService = TokenComponentService()

        // Component 1 linked to Tokens 1 and 2; Component 2 linked to Token 2
        populatedService.linkTokenToComponent(componentId = 1, tokenId = 1)
        populatedService.linkTokenToComponent(componentId = 1, tokenId = 2)
        populatedService.linkTokenToComponent(componentId = 2, tokenId = 2)
    }

    @Nested
    inner class LinkTokens {
        @Test
        fun `linking a new token to a component returns true`() {
            assertTrue(emptyService.linkTokenToComponent(componentId = 1, tokenId = 10))
            assertEquals(listOf(10), emptyService.getTokenIdsForComponent(componentId = 1))
        }

        @Test
        fun `linking same token to different components is allowed`() {
            assertTrue(emptyService.linkTokenToComponent(componentId = 1, tokenId = 5))
            assertTrue(emptyService.linkTokenToComponent(componentId = 2, tokenId = 5))
            assertEquals(listOf(1, 2), emptyService.getComponentIdsForToken(tokenId = 5))
        }

        @Test
        fun `linking duplicate token and component pair returns false`() {
            assertFalse(populatedService.linkTokenToComponent(componentId = 1, tokenId = 1))
            assertEquals(listOf(1, 2), populatedService.getTokenIdsForComponent(componentId = 1))
        }

        @Test
        fun `linkTokenToComponent delegates and blocks duplicates`() {
            assertTrue(emptyService.linkTokenToComponent(componentId = 3, tokenId = 7))
            assertFalse(emptyService.linkTokenToComponent(componentId = 3, tokenId = 7))
        }
    }

    @Nested
    inner class UnlinkTokens {
        @Test
        fun `unlinking existing token from component returns true`() {
            assertTrue(populatedService.unlinkTokenFromComponent(componentId = 1, tokenId = 1))
            assertEquals(listOf(2), populatedService.getTokenIdsForComponent(componentId = 1))
        }

        @Test
        fun `unlinking non-existent pair returns false`() {
            assertFalse(populatedService.unlinkTokenFromComponent(componentId = 1, tokenId = 99))
            assertFalse(emptyService.unlinkTokenFromComponent(componentId = 1, tokenId = 1))
        }

        @Test
        fun `unlinking from one component does not remove link on another component`() {
            assertTrue(populatedService.unlinkTokenFromComponent(componentId = 1, tokenId = 2))
            assertEquals(listOf(2), populatedService.getComponentIdsForToken(tokenId = 2))
        }
    }

    @Nested
    inner class LookupAndCleanup {
        @Test
        fun `getTokenIdsForComponent returns linked token ids or empty list`() {
            assertEquals(listOf(1, 2), populatedService.getTokenIdsForComponent(componentId = 1))
            assertTrue(emptyService.getTokenIdsForComponent(componentId = 1).isEmpty())
        }

        @Test
        fun `getComponentIdsForToken returns linked component ids or empty list`() {
            assertEquals(listOf(1, 2), populatedService.getComponentIdsForToken(tokenId = 2))
            assertTrue(emptyService.getComponentIdsForToken(tokenId = 99).isEmpty())
        }

        @Test
        fun `removeLinksForComponent removes all links for that component only`() {
            populatedService.removeLinksForComponent(componentId = 1)

            assertTrue(populatedService.getTokenIdsForComponent(componentId = 1).isEmpty())
            assertEquals(listOf(2), populatedService.getComponentIdsForToken(tokenId = 2))
        }

        @Test
        fun `removeLinksForToken removes all links for that token only`() {
            populatedService.removeLinksForToken(tokenId = 2)

            assertEquals(listOf(1), populatedService.getTokenIdsForComponent(componentId = 1))
            assertTrue(populatedService.getTokenIdsForComponent(componentId = 2).isEmpty())
        }
    }
}