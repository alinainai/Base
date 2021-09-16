package com.gas.app.test.fragment.composeLearn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gas.app.R
import com.lib.commonsdk.mvvm.fragment.BaseVMCPFragment


class ComposeLearnFragment : BaseVMCPFragment<ComposeLearnViewModel>() {

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

    @Preview
    @Composable
    fun ViewContent() {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(64.dp)
                .background(colorResource(id = R.color.public_blue))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_home),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(64.dp)
                    .clip(shape = CircleShape)
            )
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(stringResource(id = R.string.app_name))
                Spacer(modifier = Modifier.height(5.dp))
                Text("Lijiaxing")
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): ComposeLearnFragment {
            return ComposeLearnFragment()
        }
    }
}