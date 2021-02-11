package com.example.sampleandroid.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.sampleandroid.databinding.DialogAlbumInfoBinding
import com.example.sampleandroid.util.setAlbumCover
import com.example.sampleandroid.util.toDisplay
import com.example.sampleandroid.util.toPriceString
import com.example.sampleandroid.viewModel.SearchViewModel

class AlbumInfoDialogFragment : AppCompatDialogFragment() {

    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = activity?.layoutInflater?.let { DialogAlbumInfoBinding.inflate(it) }
        val headerView = activity?.layoutInflater?.let { view?.viewHeader }
        view?.let {
            builder.setView(it.root)
            viewModel.selectedAlbum.observe(this, Observer { album ->
                headerView?.let {
                    headerView.dialogHeaderNameTv.text = album.collectionName
                    headerView.dialogHeaderReleaseDateTv.text =
                        album.releaseDate.toDisplay("MMMM yyyy")
                    setAlbumCover(album.artworkUrl100, headerView.dialogHeaderIv)
                }
                view.albumInfoGenreTv.text = album.primaryGenreName
                view.albumInfoPriceTv.text = album.collectionPrice.toPriceString
                view.albumInfoCurrencyTv.text = album.currency
                view.albumInfoCopyrightTv.text = album.copyright
            })

            view.albumInfoOkBtn.setOnClickListener {
                dismiss()
            }
        }
        val dialogFragment = builder.create()
        dialogFragment.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialogFragment
    }
}
