// SampleData.kt
package com.example.myapplication_2.data

import com.example.myapplication_2.ui.model.Recipe

val sampleRecipes = listOf(
    Recipe(
        imageFileName = "morning_fresh_sandwich.jpg",
        title = "모닝 프레시 샌드위치",
        description = listOf("식빵을 토스터나 팬에 살짝 노릇하게 구워줍니다",
                "계란을 완숙으로 프라이 해줍니다",
                "토마토는 얇게 잘라주고, 상추는 씻어서 물기를 제거해줍니다",
                "빵 한 쪽에 마요네즈를 바르고 상추 -> 토마토 -> 계란 순으로 쌓아줍니다",
                "다른 빵으로 덮고 반으로 자르면 완성!"),
        rating = 1,
        author = "김제형",
        ingredients = listOf("빵","계란","토마토","상추","마요네즈")
    ),
    Recipe(
        imageFileName = "exboyfriend_toast.jpg",
        title = "전남친 토스트",
        description = listOf("식빵을 토스터기에 구워줍니다",
                "크림치즈를 식빵에 발라줍니다",
                "블루베리잼을 발라준 뒤 전자레인지에 10초간 데워주면 완성!"),
        rating = 1,
        author = "김제형",
        ingredients = listOf("빵","크림치즈","블루베리잼")
    ),
    Recipe(
        imageFileName = "soysauce_egg_rice.jpeg",
        title = "간장계란밥",
        description = listOf("밥을 그릇에 담고 간장 2큰술, 참기름 1큰술 넣고 잘 비벼줍니다",
                "그 위에 계란을 깨주고 노른자를 터뜨려줍니다",
                "전자레인지에 2분간 데워주면 완성!"),
        rating = 2,
        author = "김제형",
        ingredients = listOf("밥","계란","간장","참기름")
    ),
    Recipe(
        imageFileName = "tuna_mayo_rice.png",
        title = "참치마요덮밥",
        description = listOf("밥을 그릇에 담고 기름을 뺀 참치를 올려줍니다",
                "마요네즈를 뿌려서 비벼주면 완성!"),
        rating = 1,
        author = "김제형",
        ingredients = listOf("밥","참치","마요네즈")
    ),
    Recipe(
        imageFileName = "lemon_soba.png",
        title = "레몬 냉소바",
        description = listOf("끓는 물에 소바면을 삶은 후 찬물에 행궈 물기를 빼줍니다",
                "기호에 맞게 시판 면츠유와 물을 섞습니다",
                "식힌 면, 레몬 한 조각, 와사비를 접시에 담고 면츠유에 면을 담가 먹으면 완성!"),
        rating = 2,
        author = "김제형",
        ingredients = listOf("소바면","레몬","면츠유","와사비")
    ),
    Recipe(
        imageFileName = "sausage_eggroll.png",
        title = "소세지 계란말이",
        description = listOf("계란 3개를 풀고 소금 약간, 우유를 넣어 잘 섞습니다",
                "소세지는 길쭉하게 썰어놓습니다",
                "달군 팬에 기름을 두르고 달걀물을 얇게 부은 후 소세지를 올리고 돌돌 말아줍니다",
                "이 과정을 2~3번 반복해서 계란말이를 두껍게 만듭니다",
                "식힌 후 먹기 좋은 크기로 썰면 완성!"),
        rating = 3,
        author = "김제형",
        ingredients = listOf("계란","소세지","소금","우유","식용유")
    ),
    Recipe(
        imageFileName = "veggie_wrap.jpg",
        title = "채소쌈",
        description = listOf("끓는 물에 양배추 잎을 20~30초 정도 데친 후 찬물에 행구고 물기를 제거해줍니다",
                "두부는 으깨고, 양파는 잘게 썰어줍니다",
                "팬에 기름 없이 두부, 양파, 간장을 넣고 중불에서 수분이 날아가도록 볶아 속재료를 만들어줍니다",
                "데친 양배추에 속을 넣고 돌돌 말거나 동그랗게 감싼다",
                "접시에 케찹을 깔고, 그 위에 쌈볼을 얹어주면 완성!"),
        rating = 3,
        author = "김제형",
        ingredients = listOf("양배추잎","두부","양파","간장","케찹")
    ),
    Recipe(
        imageFileName = "carrot_fist_rice.jpg",
        title = "당근코 귀요미 주먹밥",
        description = listOf("밥에 소금간을 조금 하고 동그랗게 뭉칩니다",
                "다진 당근을 동그랗게 올려줍니다. 명란젓이나 케찹을 사용하셔도 좋습니다",
                "검은깨로 눈을, 채소잎으로 머리카락을 표현해줍니다",
                "김을 깔고 주먹밥을 얹으면 완성!"),
        rating = 1,
        author = "김제형",
        ingredients = listOf("밥","소금","당근","김")
    )
)
