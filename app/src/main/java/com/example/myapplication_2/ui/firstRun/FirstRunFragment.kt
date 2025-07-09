package com.example.myapplication_2.ui.firstRun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication_2.R
import com.example.myapplication_2.databinding.FragmentFirstRunBinding

class FirstRunFragment : Fragment() {

    private var _binding: FragmentFirstRunBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstRunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 환영 메시지
        binding.welcomeText.text = "\"넙죽이네 한끼\"에\n 오신걸 환영합니다!"
        val typeface = ResourcesCompat.getFont(requireContext(),R.font.dunggeunmo)
        binding.welcomeText.typeface = typeface

        // 계정 생성하러 가기 버튼 클릭
        binding.createAccountButton.setOnClickListener {
            val navController = findNavController()

            if (navController.currentDestination?.id == R.id.firstRunFragment) {
                navController.navigate(R.id.action_firstRunFragment_to_accountCreationFragment)
            } else {
                // 필요 시 로그만
                android.util.Log.w("FirstRunFragment", "🚫 현재 위치가 firstRunFragment가 아님")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
