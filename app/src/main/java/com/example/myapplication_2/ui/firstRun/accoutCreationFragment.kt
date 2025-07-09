package com.example.myapplication_2.ui.firstRun

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication_2.MainActivity
import com.example.myapplication_2.R
import com.example.myapplication_2.databinding.FragmentAccountCreationBinding
import com.example.myapplication_2.utils.RandomUser
import com.example.myapplication_2.utils.UserGenerator

class AccountCreationFragment : Fragment() {

    private var _binding: FragmentAccountCreationBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentUser: RandomUser
    private var isFirstUser = true  // 👈 첫 유저는 고정된 "달콤한 피자"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showNewUser()

        binding.likeButton.setOnClickListener {
            // 유저 정보 저장
            UserGenerator.setCachedUser(currentUser)
            UserGenerator.saveUserToPrefs(requireContext(), currentUser)

            // MainActivity로 이동
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)

            // IntroActivity 종료 → 뒤로가기 방지
            requireActivity().finish()
        }

        binding.dislikeButton.setOnClickListener {
            isFirstUser = false
            showNewUser()
        }
    }

    private fun showNewUser() {
        currentUser = if (isFirstUser) {
            UserGenerator.generateFixedFirstUser()
        } else {
            UserGenerator.generateNewRandomUser()
        }

        binding.generatedName.text = "@${currentUser.name}"
        binding.generatedProfile.setImageResource(currentUser.imageResId)

        // ✅ 프로필 배경을 원형으로 유지하면서 색상 적용
        runCatching {
            val color = Color.parseColor(currentUser.colorHex)
            val bg = ContextCompat.getDrawable(requireContext(), R.drawable.circle_background)
            bg?.setTint(color)
            binding.profileContainer.background = bg
        }.onFailure {
            binding.profileContainer.setBackgroundColor(Color.LTGRAY)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
