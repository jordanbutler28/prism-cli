package service

import model.Token
import model.Type
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TokenServiceTest {
    private lateinit var emptyTokenService: TokenService
    private lateinit var populatedTokenService: TokenService
    private lateinit var token1: Token
    private lateinit var token2: Token
    private lateinit var token3: Token
    private lateinit var token4: Token
    private lateinit var token5: Token

    @BeforeEach
    fun setUp() {
        token1 = Token(0, "Brand Primary", Type.COLOUR, "#0057FF")
        token2 = Token(0, "Body Font Size", Type.TYPOGRAPHY, "16px")
        token3 = Token(0, "Space Medium", Type.SPACING, "16")
        token4 = Token(0, "Card Radius", Type.BORDER_RADIUS, "8px")
        token5 = Token(0, "Elevation Low", Type.SHADOW, "0 2px 4px rgba(0,0,0,0.1)")

        emptyTokenService = TokenService()
        populatedTokenService = TokenService()

        populatedTokenService.addToken(token1)
        populatedTokenService.addToken(token2)
        populatedTokenService.addToken(token3)
        populatedTokenService.addToken(token4)
    }

    @Nested
    inner class AddTokens {
        @Test
        fun `add token to an empty service increases size correctly`() {
            assertEquals(0, emptyTokenService.getTokens().size)
            emptyTokenService.addToken(token1)
            assertEquals(1, emptyTokenService.getTokens().size)
        }

        @Test
        fun `add token to a populated service increases size correctly`() {
            assertEquals(4, populatedTokenService.getTokens().size)
            populatedTokenService.addToken(token5)
            assertEquals(5, populatedTokenService.getTokens().size)
        }

        @Test
        fun `add token stores correct values`() {
            emptyTokenService.addToken(token1)

            val retrieved = emptyTokenService.getTokens().first()
            assertEquals("Brand Primary", retrieved.name)
            assertEquals(Type.COLOUR, retrieved.type)
            assertEquals("#0057FF", retrieved.value)
        }

        @Test
        fun `add token assigns auto-incrementing ids starting at 1`() {
            emptyTokenService.addToken(token1)
            emptyTokenService.addToken(token2)

            val tokens = emptyTokenService.getTokens()
            assertEquals(1, tokens[0].id)
            assertEquals(2, tokens[1].id)
        }

        @Test
        fun `ensure ids remain unique after multiple inserts`() {
            val ids = populatedTokenService.getTokens().map { it.id }
            assertEquals(listOf(1, 2, 3, 4), ids)
        }
    }

    @Nested
    inner class GetTokens {
        @Test
        fun `getTokens returns an empty list when service is empty`() {
            assertTrue(emptyTokenService.getTokens().isEmpty())
        }

        @Test
        fun `getTokens returns all tokens when populated`() {
            val tokens = populatedTokenService.getTokens()
            assertEquals(4, tokens.size)
            assertTrue(tokens.containsAll(listOf(token1, token2, token3, token4)))
        }
    }

    @Nested
    inner class UpdateTokens {
        @Test
        fun `update existing token returns true`() {
            val updated = Token(0, "Brand Primary Updated", Type.COLOUR, "#0044CC")
            assertTrue(populatedTokenService.updateToken(1, updated))
        }

        @Test
        fun `update non-existing token returns false`() {
            val updated = Token(0, "Missing Token", Type.SPACING, "24")
            assertFalse(populatedTokenService.updateToken(99, updated))
        }

        @Test
        fun `update empty service returns false`() {
            val updated = Token(0, "Brand Primary Updated", Type.COLOUR, "#0044CC")
            assertFalse(emptyTokenService.updateToken(1, updated))
        }

        @Test
        fun `update existing token changes its stored values`() {
            val updated = Token(0, "Space Large", Type.SPACING, "32")
            populatedTokenService.updateToken(3, updated)

            val retrieved = populatedTokenService.getTokens().first { it.id == 3 }
            assertEquals("Space Large", retrieved.name)
            assertEquals(Type.SPACING, retrieved.type)
            assertEquals("32", retrieved.value)
        }

        @Test
        fun `update existing token does not change number of tokens`() {
            val sizeBefore = populatedTokenService.getTokens().size
            val updated = Token(0, "Updated Font Size", Type.TYPOGRAPHY, "18px")

            populatedTokenService.updateToken(2, updated)

            assertEquals(sizeBefore, populatedTokenService.getTokens().size)
        }

        @Test
        fun `update existing token preserves its id even if updated object has different id`() {
            val updated = Token(999, "Pinned ID Token", Type.OPACITY, "0.5")
            populatedTokenService.updateToken(4, updated)

            val retrieved = populatedTokenService.getTokens().first { it.id == 4 }
            assertEquals(4, retrieved.id)
            assertEquals("Pinned ID Token", retrieved.name)
            assertEquals(Type.OPACITY, retrieved.type)
            assertEquals("0.5", retrieved.value)
        }
    }

    @Nested
    inner class DeleteTokens {
        @Test
        fun `delete existing token returns true`() {
            assertTrue(populatedTokenService.deleteToken(1))
        }

        @Test
        fun `delete non-existing token returns false`() {
            assertFalse(populatedTokenService.deleteToken(99))
        }

        @Test
        fun `delete from empty service returns false`() {
            assertFalse(emptyTokenService.deleteToken(1))
        }

        @Test
        fun `delete from populated service reduces size by one`() {
            val sizeBefore = populatedTokenService.getTokens().size
            populatedTokenService.deleteToken(2)
            assertEquals(sizeBefore - 1, populatedTokenService.getTokens().size)
        }

        @Test
        fun `delete existing token removes correct token`() {
            populatedTokenService.deleteToken(2)
            assertFalse(populatedTokenService.getTokens().any { it.id == 2 })
        }

        @Test
        fun `delete one token does not remove other tokens`() {
            populatedTokenService.deleteToken(1)
            val remainingIds = populatedTokenService.getTokens().map { it.id }
            assertEquals(listOf(2, 3, 4), remainingIds)
        }
    }
}