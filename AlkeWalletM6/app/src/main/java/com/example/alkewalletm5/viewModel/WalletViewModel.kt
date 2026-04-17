package com.example.alkewalletm5.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.alkewalletm5.data.WalletRepository
import com.example.alkewalletm5.data.api.RetrofitClient
import com.example.alkewalletm5.data.api.TransactionDTO
import com.example.alkewalletm5.data.local.WalletDatabase
import com.example.alkewalletm5.data.local.entities.UserEntity
import com.example.alkewalletm5.data.local.entities.TransactionEntity
import com.example.alkewalletm5.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.IOException

class WalletViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WalletRepository
    val userProfile: LiveData<UserEntity?>

    private val _apiStatus = MutableLiveData<NetworkResult<TransactionDTO>>()
    val apiStatus: LiveData<NetworkResult<TransactionDTO>> = _apiStatus

    val apiResponse = MutableLiveData<NetworkResult<List<TransactionDTO>>>()

    init {
        val walletDao = WalletDatabase.getDatabase(application).walletDao()
        repository = WalletRepository(walletDao)
        userProfile = repository.getUser().asLiveData()

        viewModelScope.launch(Dispatchers.IO) {
            val usuarioExistente = repository.getUser().firstOrNull()
            if (usuarioExistente == null) {
                repository.insertUser(
                    UserEntity(
                        id = 1, nombre = "Cliente AlkeWallet", password = "123", saldo = 0
                    )
                )
            }
        }
    }

    fun realizarTransaccion(monto: Int, esDeposito: Boolean) {
        if (monto <= 0) {
            _apiStatus.value = NetworkResult.Error("El monto debe ser mayor a 0")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _apiStatus.postValue(NetworkResult.Loading())
            try {
                val montoFinal = if (esDeposito) monto else -monto
                val tipoTransaccion = if (esDeposito) "Depósito" else "Envío"

                repository.actualizarSaldo(montoFinal)
                repository.insertTransaction(
                    TransactionEntity(
                        monto = monto, tipo = tipoTransaccion, fecha = System.currentTimeMillis()
                    )
                )

                val dto = TransactionDTO(amount = monto, type = tipoTransaccion)
                val response = RetrofitClient.instance.createTransaction(dto)

                if (response.isSuccessful && response.body() != null) {
                    _apiStatus.postValue(NetworkResult.Success(response.body()!!))
                } else {
                    val msg = when (response.code()) {
                        401 -> "Sesión expirada."
                        404 -> "Servicio no encontrado."
                        else -> "Error en el servidor: ${response.code()}"
                    }
                    _apiStatus.postValue(NetworkResult.Error(msg))
                }

            } catch (e: IOException) {
                _apiStatus.postValue(NetworkResult.Error("Sin conexión a internet."))
            } catch (e: Exception) {
                _apiStatus.postValue(NetworkResult.Error("Error inesperado: ${e.message}"))
            }
        }
    }

    fun obtenerDatosDeApi() {
        viewModelScope.launch(Dispatchers.IO) {
            apiResponse.postValue(NetworkResult.Loading())
            try {
                val response = RetrofitClient.instance.getAllTransactions()
                if (response.isSuccessful && response.body() != null) {
                    apiResponse.postValue(NetworkResult.Success(response.body()!!))
                } else {
                    apiResponse.postValue(NetworkResult.Error("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                apiResponse.postValue(NetworkResult.Error("Fallo de red"))
            }
        }
    }
}