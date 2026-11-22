package service

import model.Token

class TokenService {
    private var tokens = ArrayList<Token>()
    private var lastId = 0

    private fun getId(): Int = ++lastId

    fun addToken(token: Token) {
        token.id = getId()
        tokens.add(token)
    }

    fun getTokens(): List<Token> = tokens

    fun updateToken(id: Int, updated: Token) : Boolean {
        val index = tokens.indexOfFirst { it.id == id }
        if (index != -1) {
            val oldToken = tokens[index]
            updated.id = id
            tokens[index] = updated
            return true
        }
        return false
    }

    fun deleteToken(id: Int) : Boolean = tokens.removeIf { it.id == id }
}