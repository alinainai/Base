package com.gas.app.test.fragment.composeLearn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.lib.commonsdk.mvvm.fragment.BaseVMCPFragment


class ComposeLearnFragment : BaseVMCPFragment<ComposeLearnViewModel>(){

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ViewContent()
            }
        }

    }

    @Composable
    fun ViewContent(){
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Recipe List",
                style = MaterialTheme.typography.h3
            )
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(): ComposeLearnFragment {
            return ComposeLearnFragment()
        }
    }
}