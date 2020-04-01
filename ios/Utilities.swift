func hexToUiColor(hex: Any?) -> UIColor {
    let defaultColor: UIColor = UIColor(red: 0, green: 0, blue: 0, alpha: 0)
    guard var _hex = hex as? String else { return defaultColor }
    let r, g, b, a: CGFloat
    
    if _hex.hasPrefix("#") {
        let start = _hex.index(_hex.startIndex, offsetBy: 1)
        _hex = String(_hex[start...])
    }
    
    if _hex.count == 6 {
        _hex = _hex + "ff"
    }
    
    if _hex.count != 8 {
        return defaultColor
    }

    let scanner = Scanner(string: _hex)
    var hexNumber: UInt64 = 0

    if scanner.scanHexInt64(&hexNumber) {
        r = CGFloat((hexNumber & 0xff000000) >> 24) / 255
        g = CGFloat((hexNumber & 0x00ff0000) >> 16) / 255
        b = CGFloat((hexNumber & 0x0000ff00) >> 8) / 255
        a = CGFloat(hexNumber & 0x000000ff) / 255
        
        return UIColor(red: r, green: g, blue: b, alpha: a)
    }

    return defaultColor
}