package com.mahnoosh.auth.domain.usecase.user

import com.mahnoosh.auth.domain.repository.UserRepository
import com.mahnoosh.auth.domain.usecase.user.UserUseCase

class DefaultUserUseCase(private val userRepository: UserRepository) : UserUseCase {
    override fun getUser() = userRepository.getUser()
}