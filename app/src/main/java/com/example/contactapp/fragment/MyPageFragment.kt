package com.example.contactapp.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.contactapp.activity.AddContactActivity
import com.example.contactapp.data.ActType
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.ContactDatabase
import com.example.contactapp.data.Contants
import com.example.contactapp.databinding.FragmentMyPageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData(ContactDatabase.myContact)
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            setData(ContactDatabase.myContact)
        }
        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        binding.ivMyPageShare.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", ContactDatabase.myContact.phoneNumber)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "전화번호가 클립보드에 복사되었습니다", Toast.LENGTH_SHORT).show()
        }
        binding.btnMyPageMessage.setOnClickListener {
            val phoneNumber = ContactDatabase.myContact.phoneNumber
            val smsUri = Uri.parse("smsto:$phoneNumber") //phonNumber에는 01012345678과 같은 구성.
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = smsUri
            intent.putExtra("sms_body", "") //해당 값에 전달하고자 하는 문자메시지 전달
            startActivity(intent)
        }
        binding.btnMyPageEdit.setOnClickListener {
            val intent = Intent(context, AddContactActivity::class.java)
            intent.putExtra(Contants.ActType, ActType.EDIT_MY_PAGE)
            intent.putExtra(Contants.ITEM_DATA, ContactDatabase.myContact)
            launcher.launch(intent)
        }
    }

    private fun setData(contactData: ContactData) {
        binding.tvMyPageName.text = contactData.name
        contactData.profileImage?.let { binding.ivMyPagePerson.setImageResource(it) }
        contactData.profilePath?.let { binding.ivMyPagePerson.setImageURI(it.toUri()) }
        binding.tvMyPageMobilePerson.text = contactData.phoneNumber
        binding.tvMyPageEmailPerson.text = contactData.email
        // TODO: 나머지 세팅
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}