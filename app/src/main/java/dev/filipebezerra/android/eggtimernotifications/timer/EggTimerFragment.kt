/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.filipebezerra.android.eggtimernotifications.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.filipebezerra.android.eggtimernotifications.R
import dev.filipebezerra.android.eggtimernotifications.databinding.FragmentEggTimerBinding
import dev.filipebezerra.android.eggtimernotifications.util.createChannel
import dev.filipebezerra.android.eggtimernotifications.util.getNotificationManager

class EggTimerFragment : Fragment() {

    private val viewModel: EggTimerViewModel by viewModels()

    private val TOPIC = "breakfast"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentEggTimerBinding.inflate(inflater, container, false)
        .apply {
            eggTimerViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.run{
            getNotificationManager().createChannel(
                getString(R.string.egg_notification_channel_id),
                getString(R.string.egg_notification_channel_name)
            )
        }
    }

    companion object {
        fun newInstance() = EggTimerFragment()
    }
}

