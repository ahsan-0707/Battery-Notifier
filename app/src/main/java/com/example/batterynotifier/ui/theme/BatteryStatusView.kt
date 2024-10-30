//package com.example.batterynotifier.ui.theme
//
//@Composable
//fun BatteryStatusView(batteryLevel: Int, isCharging: Boolean) {
//    val color = if (isCharging) Color.Green else Color.Red
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Battery Level: $batteryLevel%",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold
//        )
//
//        // Circle Progress Indicator
//        CircularProgressIndicator(
//            progress = batteryLevel / 100f,
//            color = color,
//            modifier = Modifier
//                .size(100.dp)
//                .padding(16.dp)
//        )
//
//        Text(
//            text = "$batteryLevel%",
//            fontSize = 24.sp,
//            color = color
//        )
//    }
//}
