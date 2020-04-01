require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name          = "onfido-rn-sdk"
  s.version       = package["version"]
  s.summary       = package["description"]
  s.homepage      = package["homepage"]
  s.license       = package["license"]
  s.authors       = package["author"]

  s.platforms     = { :ios => "10.0" }
  s.source        = { :git => "https://github.com/hughmp/onfido-rn-sdk.git", :tag => "#{s.version}" }
  s.swift_version = '5.1'

  s.source_files  = "ios/**/*.{h,m,swift}"

  s.dependency "React"
  s.dependency "Onfido", "17.0.0"
end
