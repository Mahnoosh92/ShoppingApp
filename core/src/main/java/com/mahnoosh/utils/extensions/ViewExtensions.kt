package com.mahnoosh.utils.extensions

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

fun View.shortSnackBar(message: String, action: (Snackbar.() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let { snackbar.it() }
    snackbar.show()
}

fun View.longSnackBar(message: String, action: (Snackbar.() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let { snackbar.it() }
    snackbar.show()
}

fun View.indefiniteSnackBar(message: String, action: (Snackbar.() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    action?.let { snackbar.it() }
    snackbar.show()
}

fun Snackbar.action(message: String, action: (View) -> Unit) {
    this.setAction(message, action)
}

fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        trySend(Unit).isSuccess
    }
    awaitClose { setOnClickListener(null) }
}

fun View.showAlertDialog(
    title: String,
    message: String,
    okClicked: (DialogInterface, Int) -> Unit,
    NoClicked: (DialogInterface, Int) -> Unit
): AlertDialog.Builder {
    val builder = AlertDialog.Builder(this.context)
    builder.setTitle(title)
    builder.setMessage(message)

    builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = okClicked))
    builder.setNegativeButton("NO", DialogInterface.OnClickListener(function = NoClicked))

    return builder
}