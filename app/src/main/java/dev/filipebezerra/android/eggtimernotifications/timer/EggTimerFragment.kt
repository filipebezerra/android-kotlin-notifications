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

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.messaging.FirebaseMessaging
import dev.filipebezerra.android.eggtimernotifications.R
import dev.filipebezerra.android.eggtimernotifications.databinding.FragmentEggTimerBinding
import dev.filipebezerra.android.eggtimernotifications.util.createChannel
import dev.filipebezerra.android.eggtimernotifications.util.getNotificationManager
import dev.filipebezerra.android.eggtimernotifications.util.subscribeToTopicBreakfast

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
        context?.run {
            createEggTimerNotificationChannel()
            createBreakfastNotificationChannel()
            subscribeToTopicBreakfast()
        }
    }

    private fun Context.createEggTimerNotificationChannel() =
        getNotificationManager().createChannel(
            getString(R.string.egg_notification_channel_id),
            getString(R.string.egg_notification_channel_name),
            getString(R.string.egg_notification_channel_description)
        )

    private fun Context.createBreakfastNotificationChannel() =
        getNotificationManager().createChannel(
            getString(R.string.breakfast_notification_channel_id),
            getString(R.string.breakfast_notification_channel_name),
            getString(R.string.breakfast_notification_channel_description)
        )

    private fun Context.subscribeToTopicBreakfast() =
        FirebaseMessaging.getInstance().subscribeToTopicBreakfast(this)

    companion object {
        fun newInstance() = EggTimerFragment()
    }
}

