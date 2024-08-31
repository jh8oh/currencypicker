package dev.ohjiho.currencypicker

/**
 * Enums of all the currently used ISO-4217 numbers from the ISO organization (30/04/2024)
 *
 * @see <a href="https://www.six-group.com/en/products-services/financial-information/data-standards.html">ISO data source</a>
 */
enum class CurrencyCode(val resId: Int) {
    AED(R.drawable.flag_aed),
    AFN(R.drawable.flag_afn),
    ALL(R.drawable.flag_all),
    AMD(R.drawable.flag_amd),
    ANG(R.drawable.flag_ang),
    AOA(R.drawable.flag_aoa),
    ARS(R.drawable.flag_ars),
    AUD(R.drawable.flag_aud),
    AWG(R.drawable.flag_awg),
    AZN(R.drawable.flag_azn),
    BAM(R.drawable.flag_bam),
    BBD(R.drawable.flag_bbd),
    BDT(R.drawable.flag_bdt),
    BGN(R.drawable.flag_bgn),
    BHD(R.drawable.flag_bhd),
    BIF(R.drawable.flag_bif),
    BMD(R.drawable.flag_bmd),
    BND(R.drawable.flag_bnd),
    BOB(R.drawable.flag_bob),
    BRL(R.drawable.flag_brl),
    BSD(R.drawable.flag_bsd),
    BTN(R.drawable.flag_btn),
    BWP(R.drawable.flag_bwp),
    BYN(R.drawable.flag_byn),
    BZD(R.drawable.flag_bzd),
    CAD(R.drawable.flag_cad),
    CDF(R.drawable.flag_cdf),
    CHF(R.drawable.flag_chf),
    CLP(R.drawable.flag_clp),
    CNY(R.drawable.flag_cny),
    COP(R.drawable.flag_cop),
    CRC(R.drawable.flag_crc),
    CUP(R.drawable.flag_cup),
    CVE(R.drawable.flag_cve),
    CZK(R.drawable.flag_czk),
    DJF(R.drawable.flag_djf),
    DKK(R.drawable.flag_dkk),
    DOP(R.drawable.flag_dop),
    DZD(R.drawable.flag_dzd),
    EGP(R.drawable.flag_egp),
    ERN(R.drawable.flag_ern),
    ETB(R.drawable.flag_etb),
    EUR(R.drawable.flag_eur),
    FJD(R.drawable.flag_fjd),
    FKP(R.drawable.flag_fkp),
    GBP(R.drawable.flag_gbp),
    GEL(R.drawable.flag_gel),
    GHS(R.drawable.flag_ghs),
    GIP(R.drawable.flag_gip),
    GMD(R.drawable.flag_gmd),
    GNF(R.drawable.flag_gnf),
    GTQ(R.drawable.flag_gtq),
    GYD(R.drawable.flag_gyd),
    HKD(R.drawable.flag_hkd),
    HNL(R.drawable.flag_hnl),
    HTG(R.drawable.flag_htg),
    HUF(R.drawable.flag_huf),
    IDR(R.drawable.flag_idr),
    ILS(R.drawable.flag_ils),
    INR(R.drawable.flag_inr),
    IQD(R.drawable.flag_iqd),
    IRR(R.drawable.flag_irr),
    ISK(R.drawable.flag_isk),
    JMD(R.drawable.flag_jmd),
    JOD(R.drawable.flag_jod),
    JPY(R.drawable.flag_jpy),
    KES(R.drawable.flag_kes),
    KGS(R.drawable.flag_kgs),
    KHR(R.drawable.flag_khr),
    KMF(R.drawable.flag_kmf),
    KPW(R.drawable.flag_kpw),
    KRW(R.drawable.flag_krw),
    KWD(R.drawable.flag_kwd),
    KYD(R.drawable.flag_kyd),
    KZT(R.drawable.flag_kzt),
    LAK(R.drawable.flag_lak),
    LBP(R.drawable.flag_lbp),
    LKR(R.drawable.flag_lkr),
    LRD(R.drawable.flag_lrd),
    LSL(R.drawable.flag_lsl),
    LYD(R.drawable.flag_lyd),
    MAD(R.drawable.flag_mad),
    MDL(R.drawable.flag_mdl),
    MGA(R.drawable.flag_mga),
    MKD(R.drawable.flag_mkd),
    MMK(R.drawable.flag_mmk),
    MNT(R.drawable.flag_mnt),
    MOP(R.drawable.flag_mop),
    MRU(R.drawable.flag_mru),
    MUR(R.drawable.flag_mur),
    MVR(R.drawable.flag_mvr),
    MWK(R.drawable.flag_mwk),
    MXN(R.drawable.flag_mxn),
    MYR(R.drawable.flag_myr),
    MZN(R.drawable.flag_mzn),
    NAD(R.drawable.flag_nad),
    NGN(R.drawable.flag_ngn),
    NIO(R.drawable.flag_nio),
    NOK(R.drawable.flag_nok),
    NPR(R.drawable.flag_npr),
    NZD(R.drawable.flag_nzd),
    OMR(R.drawable.flag_omr),
    PAB(R.drawable.flag_pab),
    PEN(R.drawable.flag_pen),
    PGK(R.drawable.flag_pgk),
    PHP(R.drawable.flag_php),
    PKR(R.drawable.flag_pkr),
    PLN(R.drawable.flag_pln),
    PYG(R.drawable.flag_pyg),
    QAR(R.drawable.flag_qar),
    RON(R.drawable.flag_ron),
    RSD(R.drawable.flag_rsd),
    RUB(R.drawable.flag_rub),
    RWF(R.drawable.flag_rwf),
    SAR(R.drawable.flag_sar),
    SBD(R.drawable.flag_sbd),
    SCR(R.drawable.flag_scr),
    SDG(R.drawable.flag_sdg),
    SEK(R.drawable.flag_sek),
    SGD(R.drawable.flag_sgd),
    SHP(R.drawable.flag_shp),
    SLE(R.drawable.flag_sle),
    SOS(R.drawable.flag_sos),
    SRD(R.drawable.flag_srd),
    SSP(R.drawable.flag_ssp),
    STN(R.drawable.flag_stn),
    SYP(R.drawable.flag_syp),
    SZL(R.drawable.flag_szl),
    THB(R.drawable.flag_thb),
    TJS(R.drawable.flag_tjs),
    TMT(R.drawable.flag_tmt),
    TND(R.drawable.flag_tnd),
    TOP(R.drawable.flag_top),
    TRY(R.drawable.flag_try),
    TTD(R.drawable.flag_ttd),
    TWD(R.drawable.flag_twd),
    TZS(R.drawable.flag_tzs),
    UAH(R.drawable.flag_uah),
    UGX(R.drawable.flag_ugx),
    USD(R.drawable.flag_usd),
    UYU(R.drawable.flag_uyu),
    UZS(R.drawable.flag_uzs),
    VES(R.drawable.flag_ves),
    VND(R.drawable.flag_vnd),
    VUV(R.drawable.flag_vuv),
    WST(R.drawable.flag_wst),
    XAF(R.drawable.flag_xaf),
    XCD(R.drawable.flag_xcd),
    XOF(R.drawable.flag_xof),
    XPF(R.drawable.flag_xpf),
    YER(R.drawable.flag_yer),
    ZAR(R.drawable.flag_zar),
    ZMW(R.drawable.flag_zmw),
    ZWL(R.drawable.flag_zwl);

    companion object {
        fun getPopularCurrency() = listOf(USD, EUR, JPY, GBP, CNY, AUD, CAD, CHF, HKD, SGD, SEK, KRW)
    }
}