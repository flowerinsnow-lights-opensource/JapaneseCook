package online.flowerinsnow.japanesecook;

public enum EnumStage {
    /**
     * 技能准备好了，但是还没有开
     */
    ABILITY_READY,
    /**
     * 技能开了，但是没有生效
     */
    ABILITY_FALLBACK,
    /**
     * 技能已经点击了右键
     */
    ABILITY_USING,
    /**
     * 技能冷却中
     */
    ABILITY_COOLDOWN
}
