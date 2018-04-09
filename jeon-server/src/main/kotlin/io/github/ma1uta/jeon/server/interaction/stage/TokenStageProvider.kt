package io.github.ma1uta.jeon.server.interaction.stage

import io.github.ma1uta.jeon.server.interaction.StageProvider
import io.github.ma1uta.matrix.client.model.account.AuthenticationData
import io.github.ma1uta.matrix.client.model.auth.AuthType

class TokenStageProvider : StageProvider {
    override fun stage() = AuthType.TOKEN

    override fun params() = mutableMapOf<String, String>()

    override fun authenticate(authenticationData: AuthenticationData): Boolean {
        return false
        //TODO("not implemented")
    }
}