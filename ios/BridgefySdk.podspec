
Pod::Spec.new do |s|
  s.name         = "BridgefySdk"
  s.version      = "1.0.0"
  s.summary      = "Bridgefy SDK"
  s.description  = <<-DESC
                  Bridgefy SDK wrapper
                   DESC
  s.homepage      = "https://bridgefy.me"
  s.license       = "MIT"
  # s.license     = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author        = { "Bridgefy" => "contect@bridgefy.me" }
  s.platform      = :ios, "7.0"
  s.source        = { :git => "https://bitbucket.org/bridgefy/react-native-bridgefy-sdk", :tag => "master" }
  s.source_files  = "BridgefySdk/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  s.dependency "BFTransmitter"
  #s.dependency "others"

end

