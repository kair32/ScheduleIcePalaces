package su.wolfstudio.schedule_ice.ui.components

import su.wolfstudio.schedule_ice.cashe.StateValue
import su.wolfstudio.schedule_ice.cashe.stateValue
import su.wolfstudio.schedule_ice.model.Schedule


class TimerPickerComponent(
    schedule: Schedule,
){
    val hourStart: StateValue<Int> by stateValue(schedule.getStartTimeHour())
    val minStart: StateValue<Int> by stateValue(schedule.getStartTimeMin())
    val hourEnd: StateValue<Int> by stateValue(schedule.getEndTimeHour())
    val minEnd: StateValue<Int> by stateValue(schedule.getEndTimeMin())
    val isStart: StateValue<Boolean> by stateValue(false)

    fun updateStart(isStartUpdate: Boolean) = isStart.compareAndSet(isStart.value, isStartUpdate)

    fun updateTime(hour: Int, minute: Int): Pair<Int, Int> {
        val timeStart: Int
        val timeEnd: Int
        if (isStart.value) {
            hourStart.compareAndSet(hourStart.value, hour)
            minStart.compareAndSet(minStart.value, minute)
            timeStart = hour * 60 + minute
            timeEnd = hourEnd.value * 60 + minEnd.value
        }else{
            hourEnd.compareAndSet(hourEnd.value, hour)
            minEnd.compareAndSet(minEnd.value, minute)
            timeStart = hourStart.value * 60 + minStart.value
            timeEnd = hour * 60 + minute
        }
        return Pair(timeStart, timeEnd)
    }
}