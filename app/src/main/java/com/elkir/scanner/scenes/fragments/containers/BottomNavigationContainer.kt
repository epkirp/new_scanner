package com.elkir.scanner.scenes.fragments.containers

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.elkir.scanner.R
import com.elkir.scanner.databinding.FragmentBottomNavigationBinding
import com.elkir.scanner.extensions.BackButtonBehaviour
import com.elkir.scanner.extensions.changeVisibility
import com.elkir.scanner.extensions.setupWithNavController


class BottomNavigationContainer : Fragment() {

    // https://github.com/aurelhubert/ahbottomnavigation/issues/171
    private val mLayoutKeyboardVisibilityListener = OnGlobalLayoutListener {
        val rectangle = Rect()
        val contentView = requireView()
        contentView.getWindowVisibleDisplayFrame(rectangle)
        val screenHeight = contentView.rootView.height
        val keypadHeight = screenHeight - rectangle.bottom
        val isKeyboardNowVisible = keypadHeight > screenHeight * 0.15
        if (isKeyboardVisible != isKeyboardNowVisible) {
            binding.bottomNavigationView.changeVisibility(!isKeyboardNowVisible)
        }
        isKeyboardVisible = isKeyboardNowVisible
    }

    private var isKeyboardVisible = false
    private lateinit var binding: FragmentBottomNavigationBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_navigation, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            setupBottomNavBar()
        }
    }

    override fun onResume() {
        super.onResume()
        requireView().viewTreeObserver.addOnGlobalLayoutListener(mLayoutKeyboardVisibilityListener)
    }

    override fun onPause() {
        super.onPause()
        requireView().viewTreeObserver.removeOnGlobalLayoutListener(
            mLayoutKeyboardVisibilityListener
        )
    }

    fun changeBottomNavigationVisibility(isVisible: Boolean) {
        binding.bottomNavigationView.changeVisibility(isVisible)
    }

    private fun setupBottomNavBar() {
        val bottomNavView = binding.bottomNavigationView
        val navGraphIds = listOf(R.navigation.scanner, R.navigation.video_history)

        bottomNavView.setupWithNavController(
            fragmentManager = childFragmentManager,
            navGraphIds = navGraphIds,
            backButtonBehaviour = BackButtonBehaviour.POP_HOST_FRAGMENT,
            containerId = R.id.bottomNavigationContainer,
            firstItemId = R.id.scanner,
            intent = requireActivity().intent
        )
    }
}