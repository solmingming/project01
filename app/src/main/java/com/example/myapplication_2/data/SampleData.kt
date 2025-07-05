// SampleData.kt
package com.example.myapplication_2.data

import com.example.myapplication_2.ui.model.Recipe

val sampleRecipes = listOf(
    Recipe(
        imageFileName = "morning_fresh_sandwich.jpg",
        title = "모닝 프레시 샌드위치",
        description = "1. 식빵을 토스터나 팬에 살짝 노릇하게 구워줍니다.\n" +
                "2. 계란을 완숙으로 프라이 해줍니다.\n" +
                "3. 토마토는 얇게 잘라주고, 상추는 씻어서 물기를 제거해줍니다.\n" +
                "4. 빵 한 쪽에 마요네즈를 바르고 상추 -> 토마토 -> 계란 순으로 쌓아줍니다.\n" +
                "5. 다른 빵으로 덮고 반으로 자르면 완성!",
        rating = 1,
        author = "김제형",
        ingredients = listOf("빵","계란","토마토","상추","마요네즈")
    ),
    Recipe(
        imageFileName = "exboyfriend_toast.jpg",
        title = "전남친 토스트",
        description = "1. 식빵을 토스터기에 구워줍니다.\n" +
                "2. 크림치즈를 식빵에 발라줍니다.\n" +
                "3. 블루베리잼을 발라준 뒤 전자레인지에 10초간 데워주면 완성!",
        rating = 1,
        author = "김제형",
        ingredients = listOf("빵","크림치즈","블루베리잼")
    ),
    Recipe(
        imageFileName = "soysauce_egg_rice.jpeg",
        title = "간장계란밥",
        description = "1. 밥을 그릇에 담고 간장 2큰술, 참기름 1큰술 넣고 잘 비벼줍니다.\n" +
                "2. 그 위에 계란을 깨주고 노른자를 터뜨려줍니다.\n" +
                "3. 전자레인지에 2분간 데워주면 완성!",
        rating = 2,
        author = "김제형",
        ingredients = listOf("밥","계란","간장","참기름")
    ),
    Recipe(
        imageFileName = "tuna_mayo_rice.png",
        title = "참치마요덮밥",
        description = "1. 밥을 그릇에 담고 기름을 뺀 참치를 올려줍니다.\n" +
                "2. 마요네즈를 뿌려서 비벼주면 완성!",
        rating = 1,
        author = "김제형",
        ingredients = listOf("밥","참치","마요네즈")
    ),
    Recipe(
        imageFileName = "lemon_soba.png",
        title = "레몬 냉소바",
        description = "1. 끓는 물에 소바면을 삶은 후 찬물에 행궈 물기를 빼줍니다.\n" +
                "2. 기호에 맞게 시판 면츠유와 물을 섞습니다.\n" +
                "3. 식힌 면, 레몬 한 조각, 와사비를 접시에 담고 면츠유에 면을 담가 먹으면 완성!",
        rating = 2,
        author = "김제형",
        ingredients = listOf("소바면","레몬","면츠유","와사비")
    ),
    Recipe(
        imageFileName = "sausage_eggroll.png",
        title = "소세지 계란말이",
        description = "1. 계란 3개를 풀고 소금 약간, 우유를 넣어 잘 섞습니다.\n" +
                "2. 소세지는 길쭉하게 썰어놓습니다.\n" +
                "3. 달군 팬에 기름을 두르고 달걀물을 얇게 부은 후 소세지를 올리고 돌돌 말아줍니다.\n" +
                "4. 이 과정을 2~3번 반복해서 계란말이를 두껍게 만듭니다.\n" +
                "5. 식힌 후 먹기 좋은 크기로 썰면 완성!",
        rating = 3,
        author = "김제형",
        ingredients = listOf("계란","소세지","소금","우유","식용유")
    )
)
