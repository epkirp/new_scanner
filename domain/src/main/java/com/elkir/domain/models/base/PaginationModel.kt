package com.elkir.domain.models.base

data class PaginationModel<M : Model>(
    val totalItems: Int,
    val itemsPerPage: Int,
    val countOfPages: Int,
    val model: M
)