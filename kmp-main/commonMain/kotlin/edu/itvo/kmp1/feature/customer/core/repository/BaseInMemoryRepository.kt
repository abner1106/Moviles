package edu.itvo.kmp1.feature.customer.core.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


abstract class BaseInMemoryRepository<T, ID>(
    initialData: List<T> = emptyList()
) : BaseRepository<T, ID> {
    protected val state = MutableStateFlow(initialData)

    abstract fun getId(item: T): ID

    override fun observeAll(): StateFlow<List<T>> = state.asStateFlow()

    override suspend fun findById(id: ID): T? {
        return state.value.find {
            getId(it) == id
        }
    }

    override suspend fun save(item: T, isUpdate: Boolean) {
        state.update { current ->
            val mutable = current.toMutableList()
            val index = mutable.indexOfFirst {
                getId(it) == getId(item)
            }

            if (index >= 0) {
                mutable[index] = item
            } else {
                mutable.add(item)
            }
            mutable
        }
    }

    override suspend fun deleteById(id: ID) {
        state.update { current ->
            current.filter {
                getId(it) != id
            }
        }
    }
}
