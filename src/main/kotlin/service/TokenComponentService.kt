package service

import model.TokenComponent

class TokenComponentService {
    private val tokenComponents = ArrayList<TokenComponent>()

    fun linkTokenToComponent(componentId: Int, tokenId: Int, ): Boolean {
        if (tokenComponents.any { it.componentId == componentId && it.tokenId == tokenId }) {
            return false
        }
        tokenComponents.add(TokenComponent(componentId, tokenId))
        return true
    }

    fun unlinkTokenFromComponent(componentId: Int, tokenId: Int, ): Boolean =
        tokenComponents.removeIf { it.componentId == componentId && it.tokenId == tokenId }

    fun getTokenIdsForComponent(componentId: Int): List<Int> =
        tokenComponents
            .filter { it.componentId == componentId }
            .map { it.tokenId }

    fun getComponentIdsForToken(tokenId: Int): List<Int> =
        tokenComponents
            .filter { it.tokenId == tokenId }
            .map { it.componentId }

    fun removeLinksForComponent(componentId: Int) {
        tokenComponents.removeIf { it.componentId == componentId }
    }

    fun removeLinksForToken(tokenId: Int) {
        tokenComponents.removeIf { it.tokenId == tokenId }
    }
}